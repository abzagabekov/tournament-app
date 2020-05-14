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

        /*
        coroutineScope.launch {
            val teams = withContext(Dispatchers.IO) {
                teamDataSource.getTeamsOfTournamentSync(currentTournamentId)
            }
            initTeams(teams)
        }
         */
    }

    private fun initTeams(teams: List<Team>) {
        teams.forEach {
            if (it.id == currentMatch.homeTeam) {
                homeTeam = it
            } else if (it.id == currentMatch.awayTeam) {
                awayTeam = it
            }
        }
    }

    fun finishMatch(homeTeamGoals: String, awayTeamGoals: String) {

        if (checkUserInputs(homeTeamGoals, awayTeamGoals)) return

        _eventCreateNewMatch.value = true

        coroutineScope.launch {
            currentMatch.homeTeamGoals = homeTeamGoals.toInt()
            currentMatch.awayTeamGoals = awayTeamGoals.toInt()

            updateMatch(currentMatch)

            _eventCreateNewMatch.value = false
            _navigateToTournamentMenu.value = true
        }

    }

    private fun checkUserInputs(
        homeTeamGoals: String,
        awayTeamGoals: String
    ): Boolean {
        if (homeTeamGoals.isNullOrEmpty() || awayTeamGoals.isNullOrEmpty()) {
            _eventShowScoresEmptyMessage.value = true
            return true
        }

        if (homeTeam == null || awayTeam == null) {
            return true
        }

        if (homeTeam?.id == awayTeam?.id) {
            _eventShowSameTeamErrorMessage.value = true
            return true
        }

        return false
    }

    fun onHomeTeamChanged(team: Team) {

        homeTeam = team

        coroutineScope.launch {
            val newMatch = getMatch(team.id, awayTeam!!.id)
            if (newMatch != null) {
                currentMatch = newMatch
            }
        }
    }

    fun onAwayTeamChanged(team: Team) {
        awayTeam = team

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