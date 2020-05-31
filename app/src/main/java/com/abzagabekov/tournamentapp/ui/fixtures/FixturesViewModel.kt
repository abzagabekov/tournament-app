package com.abzagabekov.tournamentapp.ui.fixtures

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.FixturesAlgorithm
import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.TYPE_KNOCKOUT
import com.abzagabekov.tournamentapp.database.MatchDao
import com.abzagabekov.tournamentapp.database.TeamDao
import com.abzagabekov.tournamentapp.database.TournamentDao
import com.abzagabekov.tournamentapp.pojo.Match
import com.abzagabekov.tournamentapp.pojo.Team
import com.abzagabekov.tournamentapp.pojo.Tournament
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class FixturesViewModel @Inject constructor(private val matchDataSource: MatchDao,
                                            private val teamDataSource: TeamDao,
                                            private val tournamentDataSource: TournamentDao) : ViewModel() {

    lateinit var fixtures: LiveData<List<Match>>
    lateinit var teams: LiveData<List<Team>>
    var currentTournamentId: Long = 0
    private var currentTournament: Tournament? = null

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToPlayMatch = MutableLiveData<Match>()
    val navigateToPlayMatch: LiveData<Match>
        get() = _navigateToPlayMatch

    private val _eventShowNextTourButton = MutableLiveData<Boolean>()
    val eventShowNextTourButton: LiveData<Boolean>
        get() = _eventShowNextTourButton

    private val _eventShowErrorMessage = MutableLiveData<Boolean>()
    val eventShowErrorMessage: LiveData<Boolean>
        get() = _eventShowErrorMessage

    private val _eventGoToNextTour = MutableLiveData<Boolean>()
    val eventGoToNextTour: LiveData<Boolean>
        get() = _eventGoToNextTour

    fun initViewModel(id: Long, resources: Resources) {
        currentTournamentId = id
        fixtures = matchDataSource.getMatchesOfTournament(currentTournamentId)
        teams = teamDataSource.getTeamsOfTournament(currentTournamentId)

        coroutineScope.launch {
            currentTournament = withContext(Dispatchers.IO) {
                tournamentDataSource.getTournament(currentTournamentId)
            }
            currentTournament?.let {
                if (it.type == resources.getStringArray(R.array.tournament_types_array)[TYPE_KNOCKOUT]) {
                    _eventShowNextTourButton.value = true
                }
            }
        }

    }

    fun onClickGoToNextTour() {

        val playedMatches = fixtures.value?.filter { it.homeTeamGoals != null }

        if (playedMatches?.size != fixtures.value?.size) {
            _eventShowErrorMessage.value = true
            return
        } else {
            _eventGoToNextTour.value = true
            coroutineScope.launch {
                createNewTourForKnockout()
                _eventGoToNextTour.value = false
            }
        }
    }

    private suspend fun createNewTourForKnockout() {

        val teamIds = ArrayList<Long>()
        fixtures.value?.forEach {
            teamIds.add( if (it.homeTeamGoals!! <= it.awayTeamGoals!!) it.awayTeam else it.homeTeam)
        }

        teams.value?.filter { it.id in teamIds }?.let {
            val newFixtures = FixturesAlgorithm(it).generateTourForKickOff(isTwoLeg = currentTournament!!.isTwoLeg)
            clearFixtures()
            insertMatches(createNewMatches(newFixtures))
        }
    }

    private fun createNewMatches(newFixtures: Set<List<MutableList<Team?>>>): List<Match> {

        val result = ArrayList<Match>()
        for (tour in newFixtures) {
            for (match in tour) {

                val homeTeam = match[0]!!
                val awayTeam = match[1]!!

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

    private suspend fun clearFixtures() {
        withContext(Dispatchers.IO) {
            fixtures.value?.forEach {
                matchDataSource.delete(it.id)
            }
        }
    }

    private suspend fun insertMatches(match: List<Match>) {
        withContext(Dispatchers.IO) {
            matchDataSource.insertMatches(match)
        }
    }

    fun doneShowErrorMessage() {
        _eventShowErrorMessage.value = false
    }

    fun onPlayMatch(match: Match) {
        _navigateToPlayMatch.value = match
    }

    fun doneNavigateToPlayMatch() {
        _navigateToPlayMatch.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}