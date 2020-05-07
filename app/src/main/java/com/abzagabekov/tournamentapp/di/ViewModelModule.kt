package com.abzagabekov.tournamentapp.di

import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.ui.home.HomeViewModel
import com.abzagabekov.tournamentapp.ui.newTournament.NewTournamentViewModel
import com.abzagabekov.tournamentapp.ui.teams.TeamsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

@Module
abstract class ViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(NewTournamentViewModel::class)
    abstract fun newTournamentViewModel(newTournamentViewModel: NewTournamentViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(TeamsViewModel::class)
    abstract fun teamsViewModel(teamsViewModel: TeamsViewModel): ViewModel
}