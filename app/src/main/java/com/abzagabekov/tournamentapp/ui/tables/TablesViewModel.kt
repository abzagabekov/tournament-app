package com.abzagabekov.tournamentapp.ui.tables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.AssistedSavedStateViewModelFactory
import com.abzagabekov.tournamentapp.database.ResultTableDao
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

class TablesViewModel @AssistedInject constructor(private val resultsDataSource: ResultTableDao, @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        const val KEY_TOURNAMENT_ID = "id"
    }

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<TablesViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): TablesViewModel
    }

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _eventShowResultTable = MutableLiveData<List<ResultTable>>()
    val eventShowResultTable: LiveData<List<ResultTable>>
        get() = _eventShowResultTable


    init {
        coroutineScope.launch {
            _eventShowResultTable.value = getResults(savedStateHandle[KEY_TOURNAMENT_ID]!!)
        }
    }

    private suspend fun getResults(id: Long): List<ResultTable> {
        return withContext(Dispatchers.IO) {
            resultsDataSource.getResults(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}