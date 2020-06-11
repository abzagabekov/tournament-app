package com.abzagabekov.tournamentapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abzagabekov.tournamentapp.pojo.KnockoutNode
import com.abzagabekov.tournamentapp.pojo.Match
import com.abzagabekov.tournamentapp.pojo.Team
import com.abzagabekov.tournamentapp.pojo.Tournament

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

@Database(entities = [Tournament::class, Team::class, Match::class, KnockoutNode::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val tournamentDao: TournamentDao
    abstract val teamDao: TeamDao
    abstract val matchDao: MatchDao
    abstract val resultTableDao: ResultTableDao
    abstract val nodesDao: KnockoutNodeDao

    companion object {
        const val DB_NAME = "tournament_app_db"
    }
}