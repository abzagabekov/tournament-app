package com.abzagabekov.tournamentapp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.pojo.Match
import com.abzagabekov.tournamentapp.pojo.Team
import com.abzagabekov.tournamentapp.pojo.Tournament

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

const val TYPE_LEAGUE = 0
const val TYPE_KNOCKOUT = 1

fun getTournaments() = arrayListOf(
    Tournament(0, "League 1", "league", 20),
    Tournament(1, "League 2", "league", 18),
    Tournament(2, "League 3", "league", 20)
)

fun getMatches() = arrayListOf(
    Match(0, 0, 0, 2, 2, 0),
    Match(1, 0, 0, 1, 3, 0),
    Match(2, 0, 0, 3, 3, 1),
    Match(3, 0, 0, 3, 0, 1),
    Match(4, 0, 0, 4, 2, 2),
    Match(5, 0, 0, 1, 1, 2)
)

interface AssistedSavedStateViewModelFactory<T : ViewModel> {
    fun create(savedStateHandle: SavedStateHandle): T
}