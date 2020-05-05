package com.abzagabekov.tournamentapp.pojo

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

data class Match(
    val id: Long,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamGoals: Int,
    val awayTeamGoals: Int,
    val tournament: Long
) {
}