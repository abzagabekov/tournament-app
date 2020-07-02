package com.abzagabekov.tournamentapp.ui.newTournament

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abzagabekov.tournamentapp.R
import com.abzagabekov.tournamentapp.ui.home.HomeFragment
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by abzagabekov on 25.06.2020.
 * email: abzagabekov@gmail.com
 */

@RunWith(AndroidJUnit4::class)
class NewTournamentFragmentTest {

    @Test
    fun testFABisDisplayed() {
        launchFragmentInContainer<NewTournamentFragment>()
        Espresso.onView(withId(R.id.btn_submit_tour))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}