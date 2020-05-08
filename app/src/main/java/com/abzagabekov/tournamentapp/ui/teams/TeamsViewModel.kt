package com.abzagabekov.tournamentapp.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.database.TeamDao
import com.abzagabekov.tournamentapp.pojo.Team
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class TeamsViewModel @Inject constructor(private val teamDataSource: TeamDao) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    lateinit var teams: LiveData<List<Team>>

    private val _eventShowNewTeamDialog = MutableLiveData<Boolean>()
    val eventShowNewTeamDialog: LiveData<Boolean>
        get() = _eventShowNewTeamDialog

    private var tournamentId: Long? = null

    fun initViewModel(tournamentId: Long) {
        this.tournamentId = tournamentId
        teams = teamDataSource.getTeamsOfTournament(tournamentId)
    }

    fun onAddNewTeam() {
        _eventShowNewTeamDialog.value = true
    }

    fun showNewTeamDialogComplete() {
        _eventShowNewTeamDialog.value = false
    }

    fun addNewTeam(name: String) {
        val newTeam = Team(name = name, tournament = tournamentId!!)
        coroutineScope.launch {
            insertTeam(newTeam)
        }
    }

    private suspend fun insertTeam(team: Team) {
        withContext(Dispatchers.IO) {
            teamDataSource.insert(team)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}