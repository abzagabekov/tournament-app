package com.abzagabekov.tournamentapp.ui.tables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.AssistedSavedStateViewModelFactory
import com.abzagabekov.tournamentapp.database.KnockoutNodeDao
import com.abzagabekov.tournamentapp.database.ResultTableDao
import com.abzagabekov.tournamentapp.pojo.KnockoutNode
import com.abzagabekov.tournamentapp.pojo.ResultTable
import com.abzagabekov.tournamentapp.pojo.Tournament
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Created by abzagabekov on 16.05.2020.
 * email: abzagabekov@gmail.com
 */

class TablesViewModel @AssistedInject constructor(private val resultsDataSource: ResultTableDao,
                                                  private val nodesDataSource: KnockoutNodeDao,
                                                  @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        const val KEY_TOURNAMENT_ID = "id"
        const val KEY_TOURNAMENT_TYPE = "type"
        const val KEY_TYPE_LEAGUE = "type_league"
        const val KEY_TYPE_KNOCKOUT = "type_knockout"
    }

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<TablesViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): TablesViewModel
    }

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val currentTournamentType: String = savedStateHandle[KEY_TOURNAMENT_TYPE]!!

    private val _eventShowResultTable = MutableLiveData<List<ResultTable>>()
    val eventShowResultTable: LiveData<List<ResultTable>>
        get() = _eventShowResultTable

    private val _eventShowResultGraph = MutableLiveData<List<KnockoutNode>>()
    val eventShowResultGraph: LiveData<List<KnockoutNode>>
        get() = _eventShowResultGraph

    init {
        coroutineScope.launch {
            val tournamentId:Long = savedStateHandle[KEY_TOURNAMENT_ID]!!
            if (currentTournamentType == savedStateHandle[KEY_TYPE_LEAGUE]) {
                _eventShowResultTable.value = getResults(tournamentId)
            } else {
                _eventShowResultGraph.value = getNodes(tournamentId)
            }
        }
    }

    private suspend fun getResults(id: Long): List<ResultTable> {
        return withContext(Dispatchers.IO) {
            resultsDataSource.getResults(id)
        }
    }

    private suspend fun getNodes(id: Long): List<KnockoutNode> {
        return withContext(Dispatchers.IO) {
            nodesDataSource.getNodesOfTournament(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}