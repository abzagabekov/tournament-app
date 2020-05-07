package com.abzagabekov.tournamentapp.di

import com.abzagabekov.tournamentapp.ui.home.HomeFragment
import com.abzagabekov.tournamentapp.ui.newTournament.NewTournamentFragment
import com.abzagabekov.tournamentapp.ui.teams.TeamsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

@Component(modules = [AppModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {

    fun inject(fragment: HomeFragment)
    fun inject(fragment: NewTournamentFragment)
    fun inject(fragment: TeamsFragment)

}