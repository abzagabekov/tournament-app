package com.abzagabekov.tournamentapp.ui.newTournament

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.FixturesAlgorithm
import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.TYPE_LEAGUE
import com.abzagabekov.tournamentapp.database.MatchDao
import com.abzagabekov.tournamentapp.database.TeamDao
import com.abzagabekov.tournamentapp.database.TournamentDao
import com.abzagabekov.tournamentapp.pojo.Match
import com.abzagabekov.tournamentapp.pojo.Team
import com.abzagabekov.tournamentapp.pojo.Tournament
import kotlinx.coroutines.*
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by abzagabekov on 06.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewTournamentViewModel @Inject constructor(private val tournamentDataSource: TournamentDao,
                                                 private val matchesDataSource: MatchDao,
                                                 private val teamDataSource: TeamDao) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var tournamentType: String? = null
    var isNeedBlankTeam = false
    var blankTeamId = 0L
    lateinit var resources: Resources

    private val _eventCreateNewTournament = MutableLiveData<Boolean>()
    val eventCreateNewTournament: LiveData<Boolean>
        get() = _eventCreateNewTournament

    private val _navigateToTournamentMenu = MutableLiveData<Tournament>()
    val navigateToTournamentMenu: LiveData<Tournament>
        get() = _navigateToTournamentMenu

    private val _eventShowErrorMessage = MutableLiveData<InputErrorCodes>()
    val eventShowErrorMessage: LiveData<InputErrorCodes>
        get() = _eventShowErrorMessage

    fun initViewModel(resources: Resources) {
        this.resources = resources
    }

    fun onChangeTournamentType(type: String) {
        tournamentType = type
    }

    fun onCreateNewTournament() {
        _eventCreateNewTournament.value = true
    }

    fun createNewTournament(name: String?, teamsCount: Int?) {

        if (!checkUserInputs(name, teamsCount)) {
            showErrorMessage(InputErrorCodes.EMPTY_FIELDS)
            return
        }

        checkTeamsCount(teamsCount!!)

        val tournament = Tournament(
            name = name!!,
            teamsCount = if (isNeedBlankTeam) teamsCount + 1 else teamsCount,
            type = tournamentType!!
        )

        coroutineScope.launch {
            insertTournament(tournament)

            val result = withContext(Dispatchers.IO) { tournamentDataSource.getLastTournament() }

            generateSchedule(if (isNeedBlankTeam) teamsCount + 1 else teamsCount, result!!.id)

            _navigateToTournamentMenu.value = result
            _eventCreateNewTournament.value = false
        }
    }

    fun showErrorMessage(errorCode: InputErrorCodes) {
        _eventShowErrorMessage.value = errorCode
    }


    fun doneNavigateToTournamentMenu() {
        _navigateToTournamentMenu.value = null
    }

    fun doneShowErrorMessage() {
        _eventShowErrorMessage.value= InputErrorCodes.CLEAR
        _eventCreateNewTournament.value = false
    }

    private suspend fun insertTournament(tournament: Tournament) {
        withContext(Dispatchers.IO) {
            tournamentDataSource.insert(tournament)
        }
    }

    private suspend fun insertMatches(match: List<Match>) {
        withContext(Dispatchers.IO) {
            matchesDataSource.insertMatches(match)
        }
    }

    private suspend fun clearBlankTeam() {
        withContext(Dispatchers.IO) {
            if (isNeedBlankTeam) {
                teamDataSource.delete(blankTeamId)
            }
        }
    }

    private fun checkUserInputs(name: String?, teamsCount: Int?): Boolean {
        return !(tournamentType == null || name == null || teamsCount == null)
    }

    private fun checkTeamsCount(teamsCount: Int) {
        if (tournamentType == resources.getStringArray(R.array.tournament_types_array)[TYPE_LEAGUE]) {
            isNeedBlankTeam = teamsCount % 2 != 0
        } else {
            var num = teamsCount
            while (num != 1 && num % 2 == 0) {
                num /= 2
            }
            if (num != 1) {
                showErrorMessage(InputErrorCodes.INVALID_TEAMS_COUNT)
            }
        }
    }

    private suspend fun generateSchedule(teamsCount: Int, tournamentId: Long) {
        val teams = createTeams(teamsCount, tournamentId)
        val fixtures = if (tournamentType == resources.getStringArray(R.array.tournament_types_array)[TYPE_LEAGUE]) {
            FixturesAlgorithm(teams).generateTours()
        } else {
            FixturesAlgorithm(teams).generateTourForKickOff()
        }
        val matches = createMatches(fixtures)
        insertMatches(matches)
        clearBlankTeam()
    }

    private suspend fun createTeams(teamsCount: Int, tournamentId: Long): List<Team> {
        val teams = ArrayList<Team>()
        for (i in 1..teamsCount) {
            teams.add(Team(name = "Team $i", tournament = tournamentId))
        }

        if (isNeedBlankTeam) {
            teams.last().isBlank = true
        }

        return withContext(Dispatchers.IO) {
            teamDataSource.insertTeams(teams)
            val res = teamDataSource.getTeamsOfTournamentSync(tournamentId)
            if (isNeedBlankTeam && res.last().isBlank) {
                blankTeamId = res.last().id
            }
            return@withContext res
        }
    }

    private fun createMatches(fixtures: Set<List<MutableList<Team?>>>): List<Match> {

        val result = ArrayList<Match>()
        for (tour in fixtures) {
            for (match in tour) {

                val homeTeam = match[0]!!
                val awayTeam = match[1]!!

                if (homeTeam.isBlank || awayTeam.isBlank)
                    continue

                result.add(
                    Match(
                    homeTeam = homeTeam.id,
                        awayTeam = awayTeam.id,
                        tournament = homeTeam.tournament
                ))
            }
        }
        return result
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

enum class InputErrorCodes {EMPTY_FIELDS, INVALID_TEAMS_COUNT, CLEAR}