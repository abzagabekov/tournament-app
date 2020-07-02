package com.abzagabekov.tournamentapp.ui.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.pojo.Tournament

/**
 * Created by abzagabekov on 06.05.2020.
 * email: abzagabekov@gmail.com
 */

class TournamentViewModel : ViewModel() {

    lateinit var currentTournament: Tournament

    private val _navigateToFixtures = MutableLiveData<Long>()
    val navigateToFixtures: LiveData<Long>
        get() = _navigateToFixtures

    private val _navigateToTables = MutableLiveData<Tournament>()
    val navigateToTables: LiveData<Tournament>
        get() = _navigateToTables

    private val _navigateToTeams = MutableLiveData<Long>()
    val navigateToTeams: LiveData<Long>
        get() = _navigateToTeams

    fun showFixtures() {
        _navigateToFixtures.value = currentTournament.id
    }

    fun showFixturesComplete() {
        _navigateToFixtures.value = null
    }

    fun showTables() {
        _navigateToTables.value = currentTournament
    }

    fun showTablesComplete() {
        _navigateToTables.value = null
    }

    fun showTeams() {
        _navigateToTeams.value = currentTournament.id
    }

    fun showTeamsComplete() {
        _navigateToTeams.value = null
    }
}