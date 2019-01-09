package com.mmteams91.todoapp.features.user.auth

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mmteams91.todoapp.R
import com.mmteams91.todoapp.app.AppActivity
import com.mmteams91.todoapp.core.extensions.showFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthFragmentTest {

    lateinit var subject: AuthFragment
    @get:Rule
    val rule = ActivityTestRule<AppActivity>(AppActivity::class.java)

    @Before
    fun setUp() {
        subject = AuthFragment.newInstance()
        rule.activity.showFragment(subject, false)
    }


    @Test
    fun ge() {
        onView(withId(android.R.id.content)).check(matches(isDisplayed()))
    }

    @Test
    fun `login with wrong credentials`() {
        onView(withId(R.id.email)).perform(typeText("eee"))
        onView(withId(R.id.password)).perform(typeText("www"))
        onView(withId(R.id.email)).check(matches(withText("eee")))
        onView(withId(R.id.password)).check(matches(withText("www")))
    }
}