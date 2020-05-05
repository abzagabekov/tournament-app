package com.abzagabekov.tournamentapp.pojo

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

data class Team(
    val id: Long,
    val name: String,
    val goalsScored: Int,
    val goalsConceded: Int,
    val tournament: Long,
    val totalPoints: Int
) {
}