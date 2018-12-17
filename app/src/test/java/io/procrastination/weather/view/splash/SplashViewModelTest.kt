package io.procrastination.weather.view.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.procrastination.foundation.domain.schedueler.Scheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class SplashViewModelTest {

    private lateinit var sut : SplashViewModel

    @Mock private lateinit var mockNavigator: SplashNavigator
    @Mock private lateinit var mockOwner: LifecycleOwner
    @Mock private lateinit var mockLifecycle: Lifecycle
    @Mock private lateinit var mockScheduler: Scheduler

    @Before
    fun setup(){
        RxJavaPlugins.reset()

        whenever(mockOwner.lifecycle).thenReturn(mockLifecycle)

        sut = SplashViewModel()
        sut.setNavigator(mockNavigator)
        sut.setLifeCycleOwner(mockOwner)
        sut.scheduler = mockScheduler
    }

    @After
    fun teardown(){
        RxJavaPlugins.reset()
    }

    @Test
    fun `WHEN onStart THEN request navigation permissions from navigator`(){
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        whenever(mockScheduler.getSubscribeOn()).thenReturn(Schedulers.trampoline())
        whenever(mockScheduler.getObserveOn()).thenReturn(Schedulers.trampoline())

        sut.start(0).test().assertComplete()
        verify(mockNavigator).requestLocationPermissions()

    }

    @Test
    fun `WHEN onPressedRequestPermissions THEN request navigation permissions from navigator`(){
        sut.onPressedRequestPermissions()
        verify(mockNavigator).requestLocationPermissions()
    }

}