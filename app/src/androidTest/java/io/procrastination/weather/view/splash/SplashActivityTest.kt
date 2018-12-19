package io.procrastination.weather.view.splash

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.squareup.spoon.Spoon
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @get:Rule val activityTest : ActivityTestRule<SplashActivity> = ActivityTestRule(SplashActivity::class.java, false, false)

    @Test
    fun testSomething(){
        val activity : SplashActivity = activityTest.launchActivity(Intent())
        Spoon.screenshot(activity, "_start")
        Assert.assertTrue(true)
    }


}