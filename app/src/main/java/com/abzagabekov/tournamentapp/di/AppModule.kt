package com.abzagabekov.tournamentapp.di

import android.content.Context
import androidx.room.Room
import com.abzagabekov.tournamentapp.database.AppDatabase
import com.abzagabekov.tournamentapp.database.MatchDao
import com.abzagabekov.tournamentapp.database.TeamDao
import com.abzagabekov.tournamentapp.database.TournamentDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

@Module
class AppModule(private val applicationContext: Context) {

    @Provides
    @Singleton
    fun tournamentDao(appDatabase: AppDatabase): TournamentDao {
        return appDatabase.tournamentDao
    }

    @Provides
    @Singleton
    fun teamDao(appDatabase: AppDatabase): TeamDao {
        return appDatabase.teamDao
    }

    @Provides
    @Singleton
    fun matchDao(appDatabase: AppDatabase): MatchDao {
        return appDatabase.matchDao
    }

    @Provides
    @Singleton
    fun appDatabase(): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, AppDatabase.DB_NAME).fallbackToDestructiveMigration().build()
    }
}