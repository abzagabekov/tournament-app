package com.abzagabekov.tournamentapp.ui.fixtures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzagabekov.tournamentapp.getMatches

/**
 * Created by abzagabekov on 07.05.2020.
 * email: abzagabekov@gmail.com
 */

class FixturesViewModel : ViewModel() {

    var fixtures = getMatches()

    private val _navigateToPlayMatch = MutableLiveData<Boolean>()
    val navigateToPlayMatch: LiveData<Boolean>
        get() = _navigateToPlayMatch

    fun onPlayMatch() {
        _navigateToPlayMatch.value = true
    }

    fun doneNavigateToPlayMatch() {
        _navigateToPlayMatch.value = false
    }


}