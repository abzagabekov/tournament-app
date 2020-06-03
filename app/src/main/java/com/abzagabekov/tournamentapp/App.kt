package com.abzagabekov.tournamentapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.abzagabekov.tournamentapp.di.AppComponent
import com.abzagabekov.tournamentapp.di.AppModule
import com.abzagabekov.tournamentapp.di.DaggerAppComponent
import com.abzagabekov.tournamentapp.ui.settings.SettingsFragment

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

        val darkModeVal = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(SettingsFragment.KEY_DARK_MODE, false)
        AppCompatDelegate.setDefaultNightMode(if (darkModeVal) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }
}