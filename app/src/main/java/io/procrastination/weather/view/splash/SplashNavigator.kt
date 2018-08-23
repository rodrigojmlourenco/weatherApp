package io.procrastination.weather.view.splash

import io.procrastination.foundation.view.FoundationNavigator

interface SplashNavigator : FoundationNavigator {

    fun goToHome()

    fun requestLocationPermissions()
}