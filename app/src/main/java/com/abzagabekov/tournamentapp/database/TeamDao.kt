package com.abzagabekov.tournamentapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abzagabekov.tournamentapp.pojo.Team

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

@Dao
interface TeamDao {

    @Insert
    fun insert(team: Team)

    @Query("SELECT * FROM Team ORDER BY id DESC")
    fun getAllTeams(): LiveData<List<Team>>

    @Query("SELECT * FROM Team WHERE id = :id")
    fun getTeam(id: Long): Team?

    @Query("DELETE FROM Team WHERE id = :key")
    fun delete(key: Long)

    @Query("DELETE FROM Team")
    fun clear()

}