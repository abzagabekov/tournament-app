package com.abzagabekov.tournamentapp.ui.fixtures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.database.MatchDao
import com.abzagabekov.tournamentapp.database.TeamDao
import com.abzagabekov.tournamentapp.pojo.Match
import com.abzagabekov.tournamentapp.pojo.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class FixturesViewModel @Inject constructor(private val matchDataSource: MatchDao, private val teamDataSource: TeamDao) : ViewModel() {

    lateinit var fixtures: LiveData<List<Match>>
    lateinit var teams: LiveData<List<Team>>
    var currentTournament: Long = 0

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToPlayMatch = MutableLiveData<Match>()
    val navigateToPlayMatch: LiveData<Match>
        get() = _navigateToPlayMatch

    fun initViewModel(id: Long) {
        currentTournament = id
        fixtures = matchDataSource.getMatchesOfTournament(currentTournament!!)
        teams = teamDataSource.getTeamsOfTournament(currentTournament!!)
    }

    fun onPlayMatch(match: Match) {
        _navigateToPlayMatch.value = match
    }

    fun doneNavigateToPlayMatch() {
        _navigateToPlayMatch.value = null
    }

}