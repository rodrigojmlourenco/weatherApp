package io.procrastination.weather.domain.model

data class LocationInfo
constructor(val latitude : Double,
            val longitude : Double,
            val city : String? = null,
            val country : String? = null,
            val zipCode : String? = null)