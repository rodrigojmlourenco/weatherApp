package io.procrastination.weather.view

import android.Manifest
import android.content.Context

const val RC_LOCATION = 1

fun Context.locationPermissions(): Array<String> {
    return arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
}