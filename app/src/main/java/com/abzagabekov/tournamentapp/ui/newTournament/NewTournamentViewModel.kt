package com.abzagabekov.tournamentapp.ui.newTournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.FixturesAlgorithm
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

    private val _eventCreateNewTournament = MutableLiveData<Boolean>()
    val eventCreateNewTournament: LiveData<Boolean>
        get() = _eventCreateNewTournament

    private val _navigateToTournamentMenu = MutableLiveData<Tournament>()
    val navigateToTournamentMenu: LiveData<Tournament>
        get() = _navigateToTournamentMenu

    private val _eventShowErrorMessage = MutableLiveData<Boolean>()
    val eventShowErrorMessage: LiveData<Boolean>
        get() = _eventShowErrorMessage

    fun onCreateNewTournament() {
        _eventCreateNewTournament.value = true
    }

    fun createNewTournament(name: String?, teamsCount: Int?) {

        if (!checkUserInputs(name, teamsCount)) {
            showErrorMessage()
            return
        }

        val tournament = Tournament(
            name = name!!,
            teamsCount = teamsCount!!,
            type = tournamentType!!
        )

        coroutineScope.launch {
            insertTournament(tournament)

            val result = withContext(Dispatchers.IO) { tournamentDataSource.getLastTournament() }

            generateSchedule(teamsCount, result!!.id)

            _navigateToTournamentMenu.value = result
            _eventCreateNewTournament.value = false
        }
    }

    fun showErrorMessage() {
        _eventShowErrorMessage.value = true
    }


    fun doneNavigateToTournamentMenu() {
        _navigateToTournamentMenu.value = null
    }

    fun doneShowErrorMessage() {
        _eventShowErrorMessage.value= false
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

    private fun checkUserInputs(name: String?, teamsCount: Int?): Boolean {
        return !(tournamentType == null || name == null || teamsCount == null)
    }

    private suspend fun generateSchedule(teamsCount: Int, tournamentId: Long) {
        val teams = createTeams(teamsCount, tournamentId)
        val fixtures = FixturesAlgorithm(teams).generateTours()
        val matches = createMatches(fixtures)
        insertMatches(matches)
    }

    private suspend fun createTeams(teamsCount: Int, tournamentId: Long): List<Team> {
        val teams = ArrayList<Team>()
        for (i in 1..teamsCount) {
            teams.add(Team(name = "Team $i", tournament = tournamentId))
        }

        return withContext(Dispatchers.IO) {
            teamDataSource.insertTeams(teams)
            teamDataSource.getTeamsOfTournamentSync(tournamentId)
        }
    }



    private fun createMatches(fixtures: Set<List<MutableList<Team?>>>): List<Match> {
        /*
        val result = ArrayList<Match>()
        val toursCount = (teams.size - 1) * 2
        val matchesCount = toursCount * teams.size / 2
        for (i in 0 until matchesCount) {
            result.add(Match(homeTeam = , awayTeam = 0, tournament = tournamentId))
        }
        return result

         */
        val result = ArrayList<Match>()
        for (tour in fixtures) {
            for (match in tour) {
                result.add(
                    Match(
                    homeTeam = match[0]!!.id,
                        awayTeam = match[1]!!.id,
                        tournament = match[0]!!.tournament
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