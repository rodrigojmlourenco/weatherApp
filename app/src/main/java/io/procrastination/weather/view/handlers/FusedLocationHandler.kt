package io.procrastination.weather.view.handlers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.procrastination.weather.domain.error.LocationRequestNotPermitedException
import io.procrastination.weather.domain.model.LocationInfo
import io.procrastination.weather.domain.protocols.LocationHandler
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import pub.devrel.easypermissions.EasyPermissions

class FusedLocationHandler(private val context: Context) : LocationHandler {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    fun setupActivity(activity: Activity): LocationHandler {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        return this
    }

    @SuppressLint("MissingPermission")
    override fun getUsersCurrentLocation(listener: Consumer<LocationInfo>) {

        if (EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            fusedLocationClient?.let {
                it.lastLocation.addOnSuccessListener { location ->

                    Observable
                        .fromCallable {
                            Geocoder(context)
                                .getFromLocation(location.latitude, location.longitude, MAX_RESULTS) ?: emptyList()
                        }
                        .map { addresses ->
                            if (addresses.isNotEmpty()) {
                                parseLocation(location, addresses[0])
                            } else {
                                parseLocation(location)
                            }
                        }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { listener.accept(it) }
                }
            }
        } else throw LocationRequestNotPermitedException()
    }

    private fun parseLocation(androidLocation: Location): LocationInfo {
        return LocationInfo(androidLocation.latitude, androidLocation.longitude)
    }

    private fun parseLocation(androidLocation: Location, address: Address): LocationInfo {
        return LocationInfo(
            androidLocation.latitude, androidLocation.longitude,
            city = address.locality,
            country = address.countryCode,
            zipCode = address.postalCode
        )
    }

    companion object {
        const val MAX_RESULTS = 5
    }
}