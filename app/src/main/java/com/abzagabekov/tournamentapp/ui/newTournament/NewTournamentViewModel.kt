package com.abzagabekov.tournamentapp.ui.newTournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.TYPE_LEAGUE
import com.abzagabekov.tournamentapp.database.TournamentDao
import com.abzagabekov.tournamentapp.pojo.Tournament
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by abzagabekov on 06.05.2020.
 * email: abzagabekov@gmail.com
 */

class NewTournamentViewModel @Inject constructor(private val tournamentDataSource: TournamentDao) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var tournamentType: String? = null

    private val _eventCreateNewTournament = MutableLiveData<Boolean>()
    val eventCreateNewTournament: LiveData<Boolean>
        get() = _eventCreateNewTournament

    private val _navigateToTournamentMenu = MutableLiveData<Tournament>()
    val navigateToTournamentMenu: LiveData<Tournament>
        get() = _navigateToTournamentMenu

    private val _eventShowErrorMessage = MutableLiveData<Boolean>()
    val eventShowErrorMessage: LiveData<Boolean>
        get() = _eventShowErrorMessage

    fun onCreateNewTournament() {
        _eventCreateNewTournament.value = true
    }

    fun createNewTournament(name: String?, teamsCount: Int?) {

        if (!checkUserInputs(name, teamsCount)) {
            showErrorMessage()
            return
        }

        val tournament = Tournament(
            name = name!!,
            teamsCount = teamsCount!!,
            type = tournamentType!!
        )

        coroutineScope.launch {
            insertTournament(tournament)

            val result = withContext(Dispatchers.IO) { tournamentDataSource.getLastTournament() }
            _navigateToTournamentMenu.value = result
            _eventCreateNewTournament.value = false
        }
    }

    fun showErrorMessage() {
        _eventShowErrorMessage.value = true
    }


    fun doneNavigateToTournamentMenu() {
        _navigateToTournamentMenu.value = null
    }

    fun doneShowErrorMessage() {
        _eventShowErrorMessage.value= false
        _eventCreateNewTournament.value = false
    }

    private suspend fun insertTournament(tournament: Tournament) {
        withContext(Dispatchers.IO) {
            tournamentDataSource.insert(tournament)
        }
    }

    private fun checkUserInputs(name: String?, teamsCount: Int?): Boolean {
        return !(tournamentType == null || name == null || teamsCount == null)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}