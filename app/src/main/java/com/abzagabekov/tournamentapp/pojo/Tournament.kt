package com.abzagabekov.tournamentapp.pojo

/**
 * Created by abzagabekov on 05.05.2020.
 * email: abzagabekov@gmail.com
 */

data class Tournament(
    val id: Long,
    val name: String,
    val type: String,
    val teamsCount: Int
)