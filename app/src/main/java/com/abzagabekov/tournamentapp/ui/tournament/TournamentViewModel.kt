package com.abzagabekov.tournamentapp.ui.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by abzagabekov on 06.05.2020.
 * email: abzagabekov@gmail.com
 */

class TournamentViewModel : ViewModel() {

    private val _navigateToFixtures = MutableLiveData<Boolean>()
    val navigateToFixtures: LiveData<Boolean>
        get() = _navigateToFixtures

    private val _navigateToTables = MutableLiveData<Boolean>()
    val navigateToTables: LiveData<Boolean>
        get() = _navigateToTables

    private val _navigateToNewMatch = MutableLiveData<Boolean>()
    val navigateToNewMatch: LiveData<Boolean>
        get() = _navigateToNewMatch

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
}