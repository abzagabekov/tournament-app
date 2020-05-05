package com.abzagabekov.tournamentapp

import com.abzagabekov.tournamentapp.pojo.Match
import com.abzagabekov.tournamentapp.pojo.Tournament

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

fun getTournaments() = arrayListOf(
    Tournament(0, "League 1", "league", 20),
    Tournament(1, "League 2", "league", 18),
    Tournament(2, "League 3", "league", 20)
)

fun getMatches() = arrayListOf(
    Match(0, "Barcelona", "Real Madrid", 2, 2, 0),
    Match(1, "Atletico", "Valencia", 1, 3, 0),
    Match(2, "Manchester United", "Chelsea", 3, 3, 1),
    Match(3, "Tottenham", "Arsenal", 3, 0, 1),
    Match(4, "Bayern", "Borussia", 4, 2, 2),
    Match(5, "Wolfsburg", "Bayer", 1, 1, 2)
)

