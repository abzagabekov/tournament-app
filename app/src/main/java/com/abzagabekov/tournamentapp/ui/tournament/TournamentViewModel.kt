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

    private val _navigateToFixtures = MutableLiveData<Boolean>()
    val navigateToFixtures: LiveData<Boolean>
        get() = _navigateToFixtures

    private val _navigateToTables = MutableLiveData<Boolean>()
    val navigateToTables: LiveData<Boolean>
        get() = _navigateToTables

    private val _navigateToNewMatch = MutableLiveData<Boolean>()
    val navigateToNewMatch: LiveData<Boolean>
        get() = _navigateToNewMatch

    private val _navigateToTeams = MutableLiveData<Long>()
    val navigateToTeams: LiveData<Long>
        get() = _navigateToTeams

    fun showFixtures() {
        _navigateToFixtures.value = true
    }

    fun showFixturesComplete() {
        _navigateToFixtures.value = false
    }

    fun showTables() {
        _navigateToTables.value = true
    }

    fun showTablesComplete() {
        _navigateToTables.value = false
    }

    fun playNewMatch() {
        _navigateToNewMatch.value = true
    }

    fun playNewMatchComplete() {
        _navigateToNewMatch.value = false
    }

    fun showTeams() {
        _navigateToTeams.value = currentTournament.id
    }

    fun showTeamsComplete() {
        _navigateToTeams.value = null
    }
}