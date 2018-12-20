package io.procrastination.weather.view.splash

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import io.procrastination.weather.di.koin.app
import io.procrastination.weather.di.koin.constants
import io.procrastination.weather.di.koin.repositories
import io.procrastination.weather.di.koin.splash
import io.procrastination.weather.di.koin.useCases
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.AutoCloseKoinTest

@RunWith(AndroidJUnit4::class)
class SplashActivityTest : AutoCloseKoinTest() {

    @get:Rule
    val activityRule: ActivityTestRule<SplashActivity>
        = ActivityTestRule(SplashActivity::class.java, false, false)

    @Before
    fun setup() {
        stopKoin()
        startKoin(listOf(app, constants, repositories, useCases, splash))
    }

    @Test
    fun testSomething() {
        activityRule.launchActivity(Intent())
        val vm = activityRule.activity.getViewModel()
        Assert.assertNotNull(vm)
    }
}