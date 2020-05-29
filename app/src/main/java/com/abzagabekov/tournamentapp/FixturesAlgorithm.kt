package com.abzagabekov.tournamentapp

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by abzagabekov on 13.05.2020.
 * email: abzagabekov@gmail.com
 */

class FixturesAlgorithm<T>(private val _teams: List<T>) {

    fun generateTours(teams: List<T> = _teams): Set<List<MutableList<T?>>> {

        val tours = MutableList(teams.size - 1){ List(teams.size / 2){ MutableList<T?>(2){null}}}
        val secondLegTours = MutableList(teams.size - 1){ List(teams.size / 2){ MutableList<T?>(2){null}}}

        var fixs = teams

        for ((index, tour) in tours.withIndex()) {

            makeNewFixtures(fixs, tour)
            secondLegTours[index] = mirrorRound(tour)

            fixs = rotateExcludeFirst(fixs)
        }

        tours.shuffle()
        secondLegTours.shuffle()

        return tours.union(secondLegTours)

    }

    fun generateTourForKickOff(teams: List<T> = _teams): Set<List<MutableList<T?>>> {
        val mutTeams = teams.toMutableList().apply { shuffle() }
        val result = List(teams.size / 2){ MutableList<T?>(2){null}}
        for (i in result.indices) {
            result[i][0] = mutTeams.removeAt(0)
            result[i][1] = mutTeams.removeAt(0)
        }
        return setOf(result)
    }

    private fun showFixtures(round: List<List<T?>>) {
        for (element in round) {
            for (j in 0..1) {
                print(element[j].toString() + " ")
            }
            println()
        }
    }

    private fun mirrorRound(round: List<List<T?>>): List<MutableList<T?>> {
        var res = List(round.size){ MutableList<T?>(2){null}}
        for (i in round.indices) {
            res[i][0] = round[i][1]
            res[i][1] = round[i][0]
        }
        return res
    }

    private fun makeNewFixtures(teams: List<T>, tour: List<MutableList<T?>>) {

        var index = 1

        for (j in 1 downTo 0) {
            if (j == 1) {
                for (i in 0 until teams.size / 2) {
                    tour[i][j] = teams[index]
                    index++
                }
            } else {
                for (i in teams.size / 2 - 1 downTo 0) {
                    if (i == 0 && j == 0) {
                        tour[i][j] = teams[0]
                    } else {
                        tour[i][j] = teams[index]
                        index++
                    }
                }
            }
        }
    }

    private fun rotateExcludeFirst(tour: List<T> = _teams): List<T> {
        val teams = tour.takeLast(tour.size - 1)
        Collections.rotate(teams, 1)
        val res = teams as MutableList
        res.add(0, tour[0])
        return res
    }
}