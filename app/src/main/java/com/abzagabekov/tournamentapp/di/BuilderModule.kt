package com.abzagabekov.tournamentapp.di

import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.AssistedSavedStateViewModelFactory
import com.abzagabekov.tournamentapp.ui.tables.TablesViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by abzagabekov on 16.05.2020.
 * email: abzagabekov@gmail.com
 */

@AssistedModule
@Module(includes = [AssistedInject_BuilderModule::class])
abstract class BuilderModule {
    @Binds
    @IntoMap
    @ViewModelKey(TablesViewModel::class)
    abstract fun bindVMFactory(f: TablesViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
}