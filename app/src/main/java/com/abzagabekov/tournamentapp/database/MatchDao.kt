package com.abzagabekov.tournamentapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abzagabekov.tournamentapp.pojo.Match

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatches(matches: List<Match>)

    @Query("SELECT * FROM `Match` ORDER BY id DESC")
    fun getAllMatches(): LiveData<List<Match>>

    @Query("SELECT * FROM `Match` WHERE tournament = :id ORDER BY id")
    fun getMatchesOfTournament(id: Long): LiveData<List<Match>>

    @Query("SELECT * FROM `Match` WHERE id = :id")
    fun getMatch(id: Long): Match?

    @Query("DELETE FROM `Match` WHERE id = :key")
    fun delete(key: Long)

    @Query("DELETE FROM `Match`")
    fun clear()

}