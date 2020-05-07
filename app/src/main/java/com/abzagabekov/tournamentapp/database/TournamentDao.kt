package com.abzagabekov.tournamentapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abzagabekov.tournamentapp.pojo.Tournament

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

@Dao
interface TournamentDao {

    @Insert
    fun insert(tournament: Tournament)

    @Query("SELECT * FROM Tournament ORDER BY id DESC")
    fun getAllTournaments(): LiveData<List<Tournament>>

    @Query("SELECT * FROM Tournament ORDER BY id DESC LIMIT 1")
    fun getLastTournament(): Tournament?

    @Query("SELECT * FROM Tournament WHERE id = :id")
    fun getTournament(id: Long): Tournament?

    @Query("DELETE FROM Tournament WHERE id = :key")
    fun delete(key: Long)

    @Query("DELETE FROM Tournament")
    fun clear()

}