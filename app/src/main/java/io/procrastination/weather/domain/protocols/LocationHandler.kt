package io.procrastination.weather.domain.protocols

import io.procrastination.weather.domain.error.LocationRequestNotPermitedException
import io.procrastination.weather.domain.model.LocationInfo
import io.reactivex.functions.Consumer

interface LocationHandler {

    @Throws(LocationRequestNotPermitedException::class)
    fun getUsersCurrentLocation(listener : Consumer<LocationInfo>)
}