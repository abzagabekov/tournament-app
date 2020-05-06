package com.abzagabekov.tournamentapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.getTournaments
import com.abzagabekov.tournamentapp.pojo.Tournament

class HomeViewModel : ViewModel() {

    private val _tournaments = MutableLiveData<List<Tournament>>().apply {
        value = getTournaments()
    }
    val tournaments: LiveData<List<Tournament>>
        get() = _tournaments


    private val _navigateToSelectedTournament = MutableLiveData<Tournament>()
    val navigateToSelectedTournament: LiveData<Tournament>
        get() = _navigateToSelectedTournament


    private val _navigateToNewTournament = MutableLiveData<Boolean>()
    val navigateToNewTournament: LiveData<Boolean>
        get() = _navigateToNewTournament

    fun displayTournamentMenu(tournament: Tournament) {
        _navigateToSelectedTournament.value = tournament
    }

    fun displayTournamentMenuComplete() {
        _navigateToSelectedTournament.value = null
    }

    fun addNewTournament() {
        _navigateToNewTournament.value = true
    }

    fun doneNavigateToNewTournament() {
        _navigateToNewTournament.value = null
    }
}