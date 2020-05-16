package com.abzagabekov.tournamentapp.ui.newMatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.database.MatchDao
import com.abzagabekov.tournamentapp.database.TeamDao
import com.abzagabekov.tournamentapp.pojo.Match
import com.abzagabekov.tournamentapp.pojo.Team
import kotlinx.android.synthetic.main.new_match_fragment.view.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewMatchViewModel @Inject constructor(private val matchDataSource: MatchDao, private val teamDataSource: TeamDao) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    lateinit var currentMatch: Match
    var currentTournamentId: Long = 0

    lateinit var teams: LiveData<List<Team>>
    private var homeTeam: Team? = null
    private var awayTeam: Team? = null

    private val _navigateToTournamentMenu = MutableLiveData<Boolean>()
    val navigateToTournamentMenu: LiveData<Boolean>
        get() = _navigateToTournamentMenu

    private val _eventCreateNewMatch = MutableLiveData<Boolean>()
    val eventCreateNewMatch: LiveData<Boolean>
        get() = _eventCreateNewMatch

    private val _eventShowScoresEmptyMessage = MutableLiveData<Boolean>()
    val eventShowScoresEmptyMessage: LiveData<Boolean>
        get() = _eventShowScoresEmptyMessage

    private val _eventShowSameTeamErrorMessage = MutableLiveData<Boolean>()
    val eventShowSameTeamErrorMessage: LiveData<Boolean>
        get() = _eventShowSameTeamErrorMessage


    fun initViewModel(tournamentId: Long, match: Match) {
        currentMatch = match
        currentTournamentId = tournamentId
        teams = teamDataSource.getTeamsOfTournament(currentTournamentId)
    }

    fun finishMatch(homeTeamGoals: String, awayTeamGoals: String) {

        if (!checkUserInputs(homeTeamGoals, awayTeamGoals)) return

        _eventCreateNewMatch.value = true

        coroutineScope.launch {

            processMatch(homeTeamGoals.toInt(), awayTeamGoals.toInt())

            updateMatch(currentMatch)
            updateTeam(homeTeam)
            updateTeam(awayTeam)

            _eventCreateNewMatch.value = false
            _navigateToTournamentMenu.value = true
        }

    }

    private fun processMatch(homeTeamGoals: Int, awayTeamGoals: Int) {
        currentMatch.homeTeamGoals = homeTeamGoals
        currentMatch.awayTeamGoals = awayTeamGoals

        homeTeam?.let {
            it.gamesPlayed = it.gamesPlayed + 1
            it.goalsScored = it.goalsScored + homeTeamGoals
            it.goalsConceded = it.goalsConceded + awayTeamGoals
            when {
                homeTeamGoals > awayTeamGoals -> {
                    it.gamesWon = it.gamesWon + 1
                    it.totalPoints = it.totalPoints + 3
                }
                homeTeamGoals == awayTeamGoals -> {
                    it.gamesDraw = it.gamesDraw + 1
                    it.totalPoints = it.totalPoints + 1
                }
                else -> {
                    it.gamesLost = it.gamesLost + 1
                }
            }
        }

        awayTeam?.let {
            it.gamesPlayed = it.gamesPlayed + 1
            it.goalsScored = it.goalsScored + awayTeamGoals
            it.goalsConceded = it.goalsConceded + homeTeamGoals
            when {
                homeTeamGoals > awayTeamGoals -> {
                    it.gamesLost = it.gamesLost + 1
                }
                homeTeamGoals == awayTeamGoals -> {
                    it.gamesDraw = it.gamesDraw + 1
                    it.totalPoints = it.totalPoints + 1
                }
                else -> {
                    it.gamesWon = it.gamesWon + 1
                    it.totalPoints = it.totalPoints + 3
                }
            }
        }

    }

    private fun checkUserInputs(
        homeTeamGoals: String,
        awayTeamGoals: String
    ): Boolean {
        if (homeTeamGoals.isNullOrEmpty() || awayTeamGoals.isNullOrEmpty()) {
            _eventShowScoresEmptyMessage.value = true
            return false
        }

        if (homeTeam == null || awayTeam == null) {
            return false
        }

        if (homeTeam?.id == awayTeam?.id) {
            _eventShowSameTeamErrorMessage.value = true
            return false
        }

        return true
    }

    fun onHomeTeamChanged(team: Team) {

        homeTeam = team

        if (awayTeam == null) return

        coroutineScope.launch {
            val newMatch = getMatch(team.id, awayTeam!!.id)
            if (newMatch != null) {
                currentMatch = newMatch
            }
        }
    }

    fun onAwayTeamChanged(team: Team) {
        awayTeam = team

        if (homeTeam == null) return

        coroutineScope.launch {
            val newMatch = getMatch(homeTeam!!.id, team.id)
            if (newMatch != null) {
                currentMatch = newMatch
            }
        }
    }

    private suspend fun updateMatch(match: Match) {
        withContext(Dispatchers.IO) {
            matchDataSource.update(match)
        }
    }

    private suspend fun updateTeam(team: Team?) {
        if (team == null) return
        withContext(Dispatchers.IO) {
            teamDataSource.update(team)
        }
    }

    private suspend fun getMatch(homeTeam: Long, awayTeam: Long): Match? {
        return withContext(Dispatchers.IO) {
            matchDataSource.getMatch(homeTeam, awayTeam)
        }
    }

    fun doneFinishMatch() {
        _navigateToTournamentMenu.value = false
    }

    fun doneShowScoresEmptyMessage() {
        _eventShowScoresEmptyMessage.value = false
    }

    fun doneShowSameTeamsErrorMessage() {
        _eventShowSameTeamErrorMessage.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}