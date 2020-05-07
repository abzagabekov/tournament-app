package com.abzagabekov.tournamentapp

import android.app.Application
import com.abzagabekov.tournamentapp.di.AppComponent
import com.abzagabekov.tournamentapp.di.AppModule
import com.abzagabekov.tournamentapp.di.DaggerAppComponent

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */
class App : Application() {

    companion object {
        private var INSTANCE: App? = null

        lateinit var appComponent: AppComponent

    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
    }
}