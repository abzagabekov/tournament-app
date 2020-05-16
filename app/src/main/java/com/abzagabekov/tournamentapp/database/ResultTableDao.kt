package com.abzagabekov.tournamentapp.database

import androidx.room.Dao
import androidx.room.Query
import com.abzagabekov.tournamentapp.pojo.ResultTable

/**
 * Created by abzagabekov on 16.05.2020.
 * email: abzagabekov@gmail.com
 */

@Dao
interface ResultTableDao {

    @Query("SELECT name as team, " +
            "gamesPlayed as games, " +
            "goalsScored - goalsConceded as goalsDifference, " +
            "gamesWon as wins, " +
            "gamesLost as loses, " +
            "gamesDraw as draws, " +
            "goalsScored as scored, " +
            "goalsConceded as conceded, " +
            "totalPoints as points " +
            "FROM Team WHERE tournament = :tournamentId ORDER BY totalPoints DESC, goalsDifference DESC, scored DESC, wins DESC")
    fun getResults(tournamentId: Long): List<ResultTable>
}