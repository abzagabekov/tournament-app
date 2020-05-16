package com.abzagabekov.tournamentapp.pojo

/**
 * Created by abzagabekov on 16.05.2020.
 * email: abzagabekov@gmail.com
 */

data class ResultTable(
    val team: String,
    val games: Int,
    val goalsDifference: Int,
    val wins: Int,
    val loses: Int,
    val draws: Int,
    val scored: Int,
    val conceded: Int,
    val points: Int
)