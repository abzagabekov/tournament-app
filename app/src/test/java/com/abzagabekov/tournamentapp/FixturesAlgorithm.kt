package com.abzagabekov.tournamentapp

/**
 * Created by abzagabekov on 25.06.2020.
 * email: abzagabekov@gmail.com
 */
open class FixturesAlgorithm<T>(private val _teams: List<T>) {
    open fun generateTours(teams: List<T> = _teams, isTwoLeg: Boolean): Set<List<MutableList<T?>>> {
        return setOf(listOf(mutableListOf()))
    }
}