package com.abzagabekov.tournamentapp.ui.newMatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.getTeams
import com.abzagabekov.tournamentapp.pojo.Team

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewMatchViewModel : ViewModel() {

    var teams = getTeams().map { it.name } as MutableList

    private val _navigateToTournamentMenu = MutableLiveData<Boolean>()
    val navigateToTournamentMenu: LiveData<Boolean>
        get() = _navigateToTournamentMenu

    fun finishMatch() {
        _navigateToTournamentMenu.value = true
    }

    fun doneFinishMatch() {
        _navigateToTournamentMenu.value = false
    }

}