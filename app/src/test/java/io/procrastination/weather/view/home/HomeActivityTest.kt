package io.procrastination.weather.view.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.whenever
import io.procrastination.sample.R
import io.procrastination.weather.TestWeatherApp
import io.procrastination.weather.domain.model.EAST
import io.procrastination.weather.domain.model.NORTH
import io.procrastination.weather.domain.model.NORTH_EAST
import io.procrastination.weather.domain.model.NORTH_WEST
import io.procrastination.weather.domain.model.SOUTH
import io.procrastination.weather.domain.model.SOUTH_EAST
import io.procrastination.weather.domain.model.SOUTH_WEST
import io.procrastination.weather.domain.model.WEST
import io.procrastination.weather.domain.model.WeatherInfo
import io.procrastination.weather.domain.protocols.NetworkHandler
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestWeatherApp::class)
class HomeActivityTest {

    private lateinit var sutController: ActivityController<HomeActivity>
    private lateinit var sutActivity: HomeActivity

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockViewModel: HomeViewModel
    @Mock
    lateinit var mockNetworkHandler: NetworkHandler

    private val fixtWeatherInfo = MutableLiveData<WeatherInfo>()
    private val fixtIsLoading = MutableLiveData<Boolean>()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        sutController = Robolectric.buildActivity(HomeActivity::class.java)
        sutController.get().mViewModel = mockViewModel
        sutController.get().mNetworkHandler = mockNetworkHandler

        whenever(mockViewModel.weatherInfo).thenReturn(fixtWeatherInfo)
        whenever(mockViewModel.isLoading).thenReturn(fixtIsLoading)

        sutActivity = sutController.create(Bundle.EMPTY).get()
    }

    @Test
    fun `WHEN go to wifi settings THEN start activity with appropriate intent`() {
        sutActivity.goToWifiSettings()

        val expected = Intent(Settings.ACTION_WIFI_SETTINGS)
        val actual = Shadows
            .shadowOf(ApplicationProvider.getApplicationContext<TestWeatherApp>()).nextStartedActivity

        Assert.assertEquals(expected.component, actual.component)
    }

    @Test
    fun `WHEN translate direction north THEN north`() {
        val expected = ApplicationProvider.getApplicationContext<TestWeatherApp>().getString(R.string.north)
        val actual = sutActivity.getDirectionAsString(NORTH)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `WHEN translate direction north-east THEN north-east`() {
        val expected = ApplicationProvider.getApplicationContext<TestWeatherApp>().getString(R.string.north_east)
        val actual = sutController.start().get().getDirectionAsString(NORTH_EAST)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `WHEN translate direction east THEN east`() {
        val expected = ApplicationProvider.getApplicationContext<TestWeatherApp>().getString(R.string.east)
        val actual = sutController.start().get().getDirectionAsString(EAST)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `WHEN translate direction south-east THEN south-east`() {
        val expected = ApplicationProvider.getApplicationContext<TestWeatherApp>().getString(R.string.south_east)
        val actual = sutController.start().get().getDirectionAsString(SOUTH_EAST)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `WHEN translate direction south THEN south`() {
        val expected = ApplicationProvider.getApplicationContext<TestWeatherApp>().getString(R.string.south)
        val actual = sutController.start().get().getDirectionAsString(SOUTH)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `WHEN translate direction south-west THEN south-west`() {
        val expected = ApplicationProvider.getApplicationContext<TestWeatherApp>().getString(R.string.south_west)
        val actual = sutController.start().get().getDirectionAsString(SOUTH_WEST)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `WHEN translate direction west THEN west`() {
        val expected = ApplicationProvider.getApplicationContext<TestWeatherApp>().getString(R.string.west)
        val actual = sutController.start().get().getDirectionAsString(WEST)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `WHEN translate direction north-west THEN north-west`() {
        val expected = ApplicationProvider.getApplicationContext<TestWeatherApp>().getString(R.string.north_west)
        val actual = sutController.start().get().getDirectionAsString(NORTH_WEST)
        Assert.assertEquals(expected, actual)
    }
}