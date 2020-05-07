package com.abzagabekov.tournamentapp.ui.newMatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.getTeams
import com.abzagabekov.tournamentapp.pojo.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewMatchViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var teams = getTeams().map { it.name } as MutableList

    private val _navigateToTournamentMenu = MutableLiveData<Boolean>()
    val navigateToTournamentMenu: LiveData<Boolean>
        get() = _navigateToTournamentMenu

    private val _eventCreateNewMatch = MutableLiveData<Boolean>()
    val eventCreateNewMatch: LiveData<Boolean>
        get() = _eventCreateNewMatch

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