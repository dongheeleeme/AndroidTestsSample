package me.dongheelee.androidtestssample

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {

    @get:Rule
    val loginActivityRule = ActivityTestRule(LoginActivity::class.java, true, true)

    @Test
    fun testSuccessLogin() {
        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()))

        onView(withId(R.id.emailEditText)).perform(typeText("tester@google.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("qwer1234"))

        onView(withId(R.id.loginButton)).perform(click())

        // TODO Check Show Dialog
    }

    @Test
    fun testFailureLogin() {
        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()))

        onView(withId(R.id.emailEditText)).perform(typeText("tester@kakao.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("qwer1234"))

        onView(withId(R.id.loginButton)).perform(click())

        // TODO Check Show Toast
    }
}
