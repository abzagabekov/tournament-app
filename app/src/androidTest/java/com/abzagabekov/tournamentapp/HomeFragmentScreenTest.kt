package com.abzagabekov.tournamentapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.abzagabekov.tournamentapp.ui.home.HomeFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by abzagabekov on 25.06.2020.
 * email: abzagabekov@gmail.com
 */

@RunWith(AndroidJUnit4::class)
class HomeFragmentScreenTest {

    @Test
    fun testFABisDisplayed() {
        launchFragmentInContainer<HomeFragment>()
        onView(withId(R.id.fab_new_tournament)).check(matches(isDisplayed()))
    }
}