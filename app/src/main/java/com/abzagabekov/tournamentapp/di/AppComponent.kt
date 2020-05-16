package com.abzagabekov.tournamentapp.di

import com.abzagabekov.tournamentapp.ui.fixtures.FixturesFragment
import com.abzagabekov.tournamentapp.ui.home.HomeFragment
import com.abzagabekov.tournamentapp.ui.newMatch.NewMatchFragment
import com.abzagabekov.tournamentapp.ui.newTournament.NewTournamentFragment
import com.abzagabekov.tournamentapp.ui.tables.TablesFragment
import com.abzagabekov.tournamentapp.ui.teams.TeamsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

@Component(modules = [AppModule::class, ViewModelModule::class, BuilderModule::class])
@Singleton
interface AppComponent {

    fun inject(fragment: HomeFragment)
    fun inject(fragment: NewTournamentFragment)
    fun inject(fragment: TeamsFragment)
    fun inject(fragment: FixturesFragment)
    fun inject(fragment: NewMatchFragment)
    fun inject(fragment: TablesFragment)

}