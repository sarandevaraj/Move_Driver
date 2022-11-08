package com.taximobility.bookingmodule.MapModule

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task

private const val REQUEST_CHECK_SETTINGS = 420

abstract class GpsFrag : Fragment() {

    private var location: Location? = null

    private var lastKnownLatLng: LatLng = LatLng(0.0, 0.0)

    private lateinit var locationCallback: LocationCallback

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }


    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        } else {
            fusedLocationClient.requestLocationUpdates(createLocationRequest(),
                    locationCallback,
                    null /* Looper */)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult?) {
//                locationResult ?: return
//                for (location in locationResult.locations) {
//                    lastKnownLatLng = LatLng(location.latitude, location.longitude)
//                    getLatlngUpdates(lastKnownLatLng)
//                }
//            }
//        }
        createLocationRequest()
    }

    fun getInitializeLocation() {
        fusedLocationClient.lastLocation
                .addOnSuccessListener(OnSuccessListener<Location> { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        lastKnownLatLng = LatLng(location.latitude, location.longitude)
                        getLatlngUpdates(lastKnownLatLng)
                    }
                })
    }

    private val requestingLocationUpdates: Boolean = true


    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    abstract fun getLatlngUpdates(lastLatLng: LatLng)

    abstract fun getLastKnownLattitudeLongtitude(lastKnownLatLng: LatLng, lastLocationReqType: Int)

    fun getLastKnownLatLng(lastLocationReqType: Int) {
        fusedLocationClient.lastLocation
                .addOnSuccessListener(OnSuccessListener<Location> { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        lastKnownLatLng = LatLng(location.latitude, location.longitude)
                        if (lastLocationReqType == 0)
                            getLatlngUpdates(lastKnownLatLng)
                        if (lastLocationReqType != 0) {
                            getLastKnownLattitudeLongtitude(lastKnownLatLng, lastLocationReqType)
                        }
                    } else {
                        lastKnownLatLng = LatLng(0.0, 0.0)
                        getLastKnownLattitudeLongtitude(lastKnownLatLng, lastLocationReqType)
//                        CToast.ShowToast(activity!!, NC.getString(R.string.Need_proper_loc))
                    }
                })
    }


    private fun getLastLocation(): Location? {
        return location
    }

    private fun makeLocationSettingsClient() {
        context?.run {
            val builder = LocationSettingsRequest.Builder().addLocationRequest(createLocationRequest())
            val client: SettingsClient = LocationServices.getSettingsClient(this)
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

            task.addOnSuccessListener { locationSettingsResponse ->
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            }

            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        exception.startResolutionForResult(this as FragmentActivity,
                                REQUEST_CHECK_SETTINGS)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        sendEx.printStackTrace()
                    }
                }
            }
        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CHECK_SETTINGS ->
                if (resultCode == Activity.RESULT_OK)
                    getLastLocationAndMoveCamera()
                else
                    alertDialog = context?.let { CommonFunctions.alertDialog(it, this, getString(R.string.prompt_location_enable), getString(R.string.ok), "", false, 2) }
        }
    }*/
}