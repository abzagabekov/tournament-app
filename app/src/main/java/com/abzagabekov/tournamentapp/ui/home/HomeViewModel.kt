package com.abzagabekov.tournamentapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.database.TournamentDao
import com.abzagabekov.tournamentapp.pojo.Tournament
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val tournamentDataSource: TournamentDao) : ViewModel() {

    val tournaments = tournamentDataSource.getAllTournaments()

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