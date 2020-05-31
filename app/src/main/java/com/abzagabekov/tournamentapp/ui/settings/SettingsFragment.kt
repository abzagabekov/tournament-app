package com.abzagabekov.tournamentapp.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.abzagabekov.tournamentapp.MainActivity
import com.abzagabekov.tournamentapp.R
import java.util.*

/**
 * Created by abzagabekov on 31.05.2020.
 * email: abzagabekov@gmail.com
 */

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        const val KEY_DARK_MODE = "dark_mode"
        const val KEY_LANGUAGE = "language"
    }

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        when (key) {
            KEY_DARK_MODE -> {
                val darkModeVal = prefs.getBoolean(KEY_DARK_MODE, false)
                AppCompatDelegate.setDefaultNightMode(if (darkModeVal) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
            }
            KEY_LANGUAGE -> {
                setLocale(prefs.getString(KEY_LANGUAGE, "en"))
            }
        }
    }


    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.getDisplayMetrics()
        val conf: Configuration = res.getConfiguration()
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(requireContext(), MainActivity::class.java)
        requireActivity().finish()
        startActivity(refresh)
    }
}
