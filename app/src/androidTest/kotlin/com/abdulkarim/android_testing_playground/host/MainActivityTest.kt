package com.abdulkarim.android_testing_playground.host

import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abdulkarim.android_testing_playground.R
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    private fun getNavController(activity: MainActivity) =
        (activity.supportFragmentManager
            .findFragmentById(R.id.mainNavHostContainerView) as NavHostFragment)
            .navController

    @Test
    fun activity_is_launched() {
        scenario.onActivity { activity ->
            assertTrue(!activity.isFinishing)
        }
    }

    @Test
    fun layout_is_displayed() {
        onView(withId(R.id.main))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navController_is_initialized() {
        scenario.onActivity { activity ->
            val navController = getNavController(activity)
            assertNotNull(navController)
        }
    }

    @Test
    fun start_destination_is_loginFragment() {
        scenario.onActivity { activity ->
            val navController = getNavController(activity)
            assertEquals(R.id.loginFragment, navController.currentDestination?.id)
        }
    }

}