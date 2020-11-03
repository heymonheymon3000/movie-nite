package com.android.movie.nite

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.android.movie.nite.app.MainActivity
import com.android.movie.nite.features.movie.ui.MovieFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun mainActivityTest() {
        val scenario = launchActivity<MainActivity>()
        onView(withId(R.id.main_activity)).check(matches(isDisplayed()))
    }

    @Test
    fun mainFragmentTest() {
        val scenario = launchFragmentInHiltContainer<MovieFragment>()
        onView(withId(R.id.movieFragment)).check(matches(isDisplayed()))
    }
}