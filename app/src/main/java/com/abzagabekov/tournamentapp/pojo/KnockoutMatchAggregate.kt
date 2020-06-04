package com.abzagabekov.tournamentapp.pojo

/**
 * Created by abzagabekov on 03.06.2020.
 * email: abzagabekov@gmail.com
 */

data class KnockoutMatchAggregate(
    val teams: Pair<Long, Long>,
    var firstTeamHomeGoals: Int = 0,
    var firstTeamAwayGoals: Int = 0,
    var secondTeamHomeGoals: Int = 0,
    var secondTeamAwayGoals: Int = 0
) {
}