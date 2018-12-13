package io.procrastination.weather.view.home

import io.procrastination.foundation.view.FoundationNavigator
import io.procrastination.weather.domain.model.Direction

interface HomeNavigator : FoundationNavigator {

    fun getDirectionAsString(@Direction direction: Int): String

    fun goToWifiSettings()
}