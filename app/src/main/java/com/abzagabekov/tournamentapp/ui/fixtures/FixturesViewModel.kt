package com.abzagabekov.tournamentapp.ui.fixtures

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.TYPE_KNOCKOUT
import com.abzagabekov.tournamentapp.database.MatchDao
import com.abzagabekov.tournamentapp.database.TeamDao
import com.abzagabekov.tournamentapp.database.TournamentDao
import com.abzagabekov.tournamentapp.pojo.Match
import com.abzagabekov.tournamentapp.pojo.Team
import com.abzagabekov.tournamentapp.pojo.Tournament
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class FixturesViewModel @Inject constructor(private val matchDataSource: MatchDao,
                                            private val teamDataSource: TeamDao,
                                            private val tournamentDataSource: TournamentDao) : ViewModel() {

    lateinit var fixtures: LiveData<List<Match>>
    lateinit var teams: LiveData<List<Team>>
    var currentTournamentId: Long = 0
    private var currentTournament: Tournament? = null

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToPlayMatch = MutableLiveData<Match>()
    val navigateToPlayMatch: LiveData<Match>
        get() = _navigateToPlayMatch

    private val _eventShowNextTourButton = MutableLiveData<Boolean>()
    val eventShowNextTourButton: LiveData<Boolean>
        get() = _eventShowNextTourButton

    fun initViewModel(id: Long, resources: Resources) {
        currentTournamentId = id
        fixtures = matchDataSource.getMatchesOfTournament(currentTournamentId)
        teams = teamDataSource.getTeamsOfTournament(currentTournamentId)

        coroutineScope.launch {
            currentTournament = withContext(Dispatchers.IO) {
                tournamentDataSource.getTournament(currentTournamentId)
            }
            currentTournament?.let {
                if (it.type == resources.getStringArray(R.array.tournament_types_array)[TYPE_KNOCKOUT]) {
                    _eventShowNextTourButton.value = true
                }
            }
        }

    }

    fun onPlayMatch(match: Match) {
        _navigateToPlayMatch.value = match
    }

    fun doneNavigateToPlayMatch() {
        _navigateToPlayMatch.value = null
    }

}