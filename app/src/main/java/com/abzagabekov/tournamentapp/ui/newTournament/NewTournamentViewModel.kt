package com.abzagabekov.tournamentapp.ui.newTournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.pojo.Tournament
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Created by abzagabekov on 06.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewTournamentViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var tournamentType: String? = null

    private val _eventCreateNewTournament = MutableLiveData<Boolean>()
    val eventCreateNewTournament: LiveData<Boolean>
        get() = _eventCreateNewTournament

    private val _navigateToTournamentMenu = MutableLiveData<Tournament>()
    val navigateToTournamentMenu: LiveData<Tournament>
        get() = _navigateToTournamentMenu

    fun onCreateNewTournament() {
        //add new tournament to db
        _eventCreateNewTournament.value = true
        coroutineScope.launch {
            TimeUnit.MILLISECONDS.sleep(3000)
            _eventCreateNewTournament.value = false
            // navigate to tournament menu
            _navigateToTournamentMenu.value = Tournament(100, "League 100", "League", 16)
        }
    }

    fun doneNavigateToTournamentMenu() {
        _navigateToTournamentMenu.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}