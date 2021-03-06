package com.abzagabekov.tournamentapp.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.database.KnockoutNodeDao
import com.abzagabekov.tournamentapp.database.TeamDao
import com.abzagabekov.tournamentapp.pojo.KnockoutNode
import com.abzagabekov.tournamentapp.pojo.Team
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class TeamsViewModel @Inject constructor(private val teamDataSource: TeamDao,
                                         private val nodesDataSource: KnockoutNodeDao) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    lateinit var teams: LiveData<List<Team>>

    private val _eventShowNewTeamDialog = MutableLiveData<Team>()
    val eventShowNewTeamDialog: LiveData<Team>
        get() = _eventShowNewTeamDialog

    private var tournamentId: Long? = null

    fun initViewModel(tournamentId: Long) {
        this.tournamentId = tournamentId
        teams = teamDataSource.getTeamsOfTournament(tournamentId)
    }

    fun onEditTeam(team: Team) {
        _eventShowNewTeamDialog.value = team
    }

    fun showNewTeamDialogComplete() {
        _eventShowNewTeamDialog.value = null
    }

    fun editTeam(name: String) {
        coroutineScope.launch {
            val newTeam = _eventShowNewTeamDialog.value ?: return@launch
            newTeam.name = name
            updateTeam(newTeam)
            updateKnockoutNodes(newTeam)
            showNewTeamDialogComplete()
        }
    }

    private suspend fun updateTeam(team: Team) {
        withContext(Dispatchers.IO) {
            teamDataSource.update(team)
        }
    }

    private suspend fun updateKnockoutNodes(team: Team) {
        withContext(Dispatchers.IO) {
            val nodes = nodesDataSource.getNodesOfTeam(team.id)
            nodes?.let {
                it.forEach {node ->
                    updateNodeName(node, team)
                }
                nodesDataSource.updateNodes(nodes)
            }
        }
    }

    private fun updateNodeName(
        node: KnockoutNode,
        team: Team
    ) {
        val clearName = node.name.trim()
        var spaceCount = node.name.length - clearName.length
        var newName = team.name
        while (spaceCount != 0) {
            newName += " "
            spaceCount--
        }
        node.name = newName
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}