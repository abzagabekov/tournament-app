package com.abzagabekov.tournamentapp.ui.newMatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.database.MatchDao
import com.abzagabekov.tournamentapp.database.TeamDao
import com.abzagabekov.tournamentapp.getTeams
import com.abzagabekov.tournamentapp.pojo.Match
import com.abzagabekov.tournamentapp.pojo.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewMatchViewModel @Inject constructor(private val matchDao: MatchDao, private val teamDao: TeamDao) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var currentMatch: Match? = null
    var currentTournamentId: Long = 0

    lateinit var teams: LiveData<List<Team>>

    private val _navigateToTournamentMenu = MutableLiveData<Boolean>()
    val navigateToTournamentMenu: LiveData<Boolean>
        get() = _navigateToTournamentMenu

    private val _eventCreateNewMatch = MutableLiveData<Boolean>()
    val eventCreateNewMatch: LiveData<Boolean>
        get() = _eventCreateNewMatch

    fun initViewModel(tournamentId: Long, match: Match?) {
        currentMatch = match
        currentTournamentId = tournamentId
        teams = teamDao.getTeamsOfTournament(currentTournamentId)
    }

    fun finishMatch() {
        _eventCreateNewMatch.value = true
        coroutineScope.launch {
            TimeUnit.MILLISECONDS.sleep(3000)
            _eventCreateNewMatch.value = false

            _navigateToTournamentMenu.value = true
        }

    }

    fun doneFinishMatch() {
        _navigateToTournamentMenu.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}