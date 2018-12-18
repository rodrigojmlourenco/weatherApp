package io.procrastination.weather.view.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.flextrade.jfixture.FixtureAnnotations
import com.flextrade.jfixture.annotations.Fixture
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.procrastination.foundation.domain.schedueler.Scheduler
import io.procrastination.weather.domain.UseCaseGetWeatherInfo
import io.procrastination.weather.domain.error.CachedInformationIsTooOldException
import io.procrastination.weather.domain.error.LocationRequestNotPermitedException
import io.procrastination.weather.domain.error.NoInformationAvailableToPresentToTheUserException
import io.procrastination.weather.domain.model.LocationInfo
import io.procrastination.weather.domain.model.WeatherInfo
import io.procrastination.weather.domain.protocols.LocationHandler
import io.procrastination.weather.domain.protocols.NetworkHandler
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.text.SimpleDateFormat
import java.util.Locale

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class HomeViewModelTest {

    private lateinit var sut: HomeViewModel

    @Mock lateinit var mockGetWeatherUseCase: UseCaseGetWeatherInfo
    @Mock lateinit var mockLocationHandler: LocationHandler
    @Mock lateinit var mockNetworkHandler: NetworkHandler
    @Mock lateinit var mockNavigator: HomeNavigator
    @Mock lateinit var mockOwner: LifecycleOwner
    @Mock lateinit var mockLifecycle: Lifecycle
    @Mock lateinit var mockScheduler: Scheduler

    @Fixture lateinit var fixtLocationInfo: LocationInfo
    @Fixture lateinit var fixtWeatherInfo: WeatherInfo

    @get:Rule val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        FixtureAnnotations.initFixtures(this)
        whenever(mockOwner.lifecycle).thenReturn(mockLifecycle)

        whenever(mockScheduler.getSubscribeOn()).thenReturn(Schedulers.trampoline())
        whenever(mockScheduler.getObserveOn()).thenReturn(Schedulers.trampoline())

        sut = HomeViewModel()

        sut.setGetWeatherInfoUseCase(mockGetWeatherUseCase)
        sut.setLocationHandler(mockLocationHandler)
        sut.setNetworkHandler(mockNetworkHandler)
        sut.setLifeCycleOwner(mockOwner)
        sut.setNavigator(mockNavigator)
        sut.scheduler = mockScheduler
    }

    @Test
    fun `WHEN pressed refresh weather GIVEN has network THEN do not go to wifi settings`() {
        whenever(mockNetworkHandler.hasNetworkConnectivity()).thenReturn(true)
        whenever(mockLocationHandler.getUsersCurrentLocation()).thenReturn(Single.just(fixtLocationInfo))
        whenever(mockGetWeatherUseCase.createInteractorObservable(fixtLocationInfo)).thenReturn(Observable.just(fixtWeatherInfo))

        sut.onPressedRefreshWeather()

        verify(mockNavigator, never()).goToWifiSettings()
    }

    @Test
    fun `WHEN pressed refresh weather GIVEN has NO network THEN go to wifi settings`() {
        whenever(mockNetworkHandler.hasNetworkConnectivity()).thenReturn(false)

        sut.onPressedRefreshWeather()

        verify(mockNavigator).goToWifiSettings()
    }

    @Test
    fun `WHEN load weather info GIVEN has location THEN populate the view model`() {
        whenever(mockLocationHandler.getUsersCurrentLocation()).thenReturn(Single.just(fixtLocationInfo))
        whenever(mockGetWeatherUseCase.createInteractorObservable(fixtLocationInfo)).thenReturn(Observable.just(fixtWeatherInfo))

        sut.loadWeatherInfo()

        Assert.assertEquals(fixtWeatherInfo, sut.weatherInfo.value)
        Assert.assertEquals(fixtWeatherInfo.condition, sut.condition.get())
        Assert.assertEquals("${fixtWeatherInfo.temperature}ºc", sut.temperature.get())
        Assert.assertEquals("${fixtWeatherInfo.windSpeed}mph", sut.windspeed.get())
        Assert.assertEquals(
                SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK).format(fixtWeatherInfo.lastUpdatedAt),
                sut.lastUpdate.get()
        )
        Assert.assertEquals(
                "${fixtWeatherInfo.location.city}, ${fixtWeatherInfo.location.country}",
                sut.location.get()
        )
        Assert.assertTrue(sut.hasFreshData.get())
    }

    @Test
    fun `WHEN load weather info GIVEN no location permissions THEN show error`() {

        val fxtError = LocationRequestNotPermitedException()

        whenever(mockLocationHandler.getUsersCurrentLocation())
                .thenReturn(Single.error<LocationInfo>(fxtError))

        sut.loadWeatherInfo()

        verify(mockNavigator).handleError(fxtError)
    }

    @Test
    fun `WHEN load weather info GIVEN has location BUT cached information is too old THEN set stale data`() {

        val fxtError = CachedInformationIsTooOldException()

        whenever(mockLocationHandler.getUsersCurrentLocation()).thenReturn(Single.just(fixtLocationInfo))

        whenever(mockGetWeatherUseCase.createInteractorObservable(fixtLocationInfo))
                .thenReturn(Observable.error<WeatherInfo>(fxtError))

        sut.loadWeatherInfo()

        verify(mockNavigator, never()).handleError(fxtError)
        Assert.assertFalse(sut.hasFreshData.get())
    }

    @Test
    fun `WHEN load weather info GIVEN has location BUT no info to present THEN set stale data`() {

        val fxtError = NoInformationAvailableToPresentToTheUserException()

        whenever(mockLocationHandler.getUsersCurrentLocation()).thenReturn(Single.just(fixtLocationInfo))

        whenever(mockGetWeatherUseCase.createInteractorObservable(fixtLocationInfo))
                .thenReturn(Observable.error<WeatherInfo>(fxtError))

        sut.loadWeatherInfo()

        verify(mockNavigator, never()).handleError(fxtError)
        Assert.assertFalse(sut.hasFreshData.get())
    }

    @Test
    fun `WHEN load weather info GIVEN has location BUT something went wrong THEN show error`() {

        val fxtError = Exception()

        whenever(mockLocationHandler.getUsersCurrentLocation()).thenReturn(Single.just(fixtLocationInfo))

        whenever(mockGetWeatherUseCase.createInteractorObservable(fixtLocationInfo))
                .thenReturn(Observable.error<WeatherInfo>(fxtError))

        sut.loadWeatherInfo()

        verify(mockNavigator).handleError(fxtError)
    }
}