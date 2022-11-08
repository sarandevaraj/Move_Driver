package com.taximobility.bookingmodule.MapModule

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import android.util.Log
import android.view.View
import com.taximobility.R
import com.taximobility.interfaces.GetAddress
import com.taximobility.roomDB.GeocoderModel
import com.taximobility.util.AddressFromLatLng
import com.taximobility.util.NC
import com.taximobility.util.SessionSave
import com.taximobility.util.TaxiUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions

private const val zoom = 18f

abstract class MapFragment : GpsFrag(),
        OnMapReadyCallback, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraIdleListener, GetAddress {

    private lateinit var map: GoogleMap
    private var mapFragments: SupportMapFragment? = null

    //camera move listener like 1 and 3 handled for drag and idle whether need to get address
    private var cameraMove: Int = 0

    private var address: AsyncTask<String, String, GeocoderModel>? = null
    private var handlerServercall: Handler? = null

    private var dragLatLng: LatLng? = null
    private var dragTempLatLng: LatLng? = null
    private var currentLatLng: LatLng = LatLng(0.0, 0.0)
    private var pickupLatLng: LatLng = LatLng(0.0, 0.0)
    //to address call 200 meters once
    private var pickupLocation: String = ""
    //to reduce address call when address select from suggestion
    private var pickDropLoc: String = ""
    //to know whether address in progress
    private var addressRunning = false
    //To get the address after current icon click
    private var currentIconClick = false
    private var isneedAddress = true

    fun initializeMap(view: View) {
        println("initializeMap")
        if (activity != null) {
            handlerServercall = Handler(Looper.getMainLooper())
            mapFragments = childFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment?
            if (mapFragments != null) {
                println("initializeMapmapFragments")
                mapFragments!!.getMapAsync(this)
            }
        }
        /**
         * Current location icon click
         */
        view.findViewById<View>(R.id.mov_cur_loc).setOnClickListener {
            getLastKnownLatLng(0)
            pickDropLoc = ""
            isneedAddress = true
            currentIconClick = true
        }

    }

    fun cameraChangeListeners(boolean: Boolean) {
        if (boolean) {
            map.setOnCameraMoveStartedListener(this)
            map.setOnCameraIdleListener(this)
        } else {
            map.setOnCameraMoveStartedListener(null)
            map.setOnCameraIdleListener(null)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        println("onMapReady")
        map = googleMap
        map.let { map ->

            if (currentLatLng.latitude != 0.0) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoom))
            }
            mapGpsInitialized(map, currentLatLng)


            if (activity != null) {
                setMapStyle()
                if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                map.uiSettings.isMapToolbarEnabled = false
                map.uiSettings.isCompassEnabled = false
                map.isMyLocationEnabled = true
                map.uiSettings.isMyLocationButtonEnabled = false
                cameraChangeListeners(true)
                getInitializeLocation()
            }
        }
    }

    override fun onCameraMoveStarted(reason: Int) {
        cameraMove = reason
        if (cameraMove == 1) {
            pickDropLoc = ""
            addressRunning = true
            setDraggedAddress(0.0, 0.0, NC.getString(R.string.fetching_address))
        }
    }

    override fun onCameraIdle() {
        dragLatLng = LatLng(map.cameraPosition.target.latitude, map.cameraPosition.target.longitude)

        if ((cameraMove == 1 || cameraMove == 3) && isneedAddress) {
            if (address != null)
                address!!.cancel(true)
            if (address == null || address!!.status != AsyncTask.Status.PENDING && address!!.status != AsyncTask.Status.RUNNING) {
                if (dragTempLatLng != null) {
                    if (dragTempLatLng!!.latitude != dragLatLng!!.latitude) {
                        if (dragLatLng!!.latitude != 0.0 && dragLatLng!!.longitude != 0.0) {
                            if (pickDropLoc == "") {
                                addressRunning = true
                                address = AddressFromLatLng(activity, LatLng(dragLatLng!!.latitude, dragLatLng!!.longitude), this@MapFragment).execute()
                            } else {
                                pickDropLoc = ""
                            }
                            dragTempLatLng = dragLatLng
                        }
                    } else {
                        if (currentIconClick) {
                            if (dragLatLng!!.latitude != 0.0 && dragLatLng!!.longitude != 0.0) {
                                addressRunning = true
                                address = AddressFromLatLng(activity, LatLng(dragLatLng!!.latitude, dragLatLng!!.longitude), this@MapFragment).execute()
                                dragTempLatLng = dragLatLng
                            }
                        }
                    }
                } else {
                    if (dragLatLng!!.latitude != 0.0 && dragLatLng!!.longitude != 0.0) {
                        addressRunning = true
                        address = AddressFromLatLng(activity, LatLng(dragLatLng!!.latitude, dragLatLng!!.longitude), this@MapFragment).execute()
                        dragTempLatLng = dragLatLng
                    }
                }
            }
        }
        isneedAddress = true
    }

    override fun setaddress(latitude: Double, longitude: Double, Address: String?) {
        try {
            if (activity != null && Address != null) {
                addressRunning = false
                setDraggedAddress(latitude, longitude, Address)
                pickDropLoc = ""
                currentIconClick = false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract fun setDraggedAddress(latitude: Double, longitude: Double, address: String)

    abstract fun mapGpsInitialized(map: GoogleMap, currLatLng: LatLng)

    private fun setMapStyle() {
        try {
            // Customise the styling of the base map using a JSON object defined in a raw resource file.
            val success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity!!, R.raw.map_style))
            if (!success) {
                println("Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            println("Can't find style. Error: ")
        }
    }

    fun moveTothisLocation(mLatLng: LatLng, isneedAddress: Boolean, address: String) {
        this.isneedAddress = isneedAddress
        this.pickDropLoc = address
        if (::map.isInitialized)
            map.moveCamera(CameraUpdateFactory.newLatLng(mLatLng))
    }

    fun updatedPickupLatLng(mLatLng: LatLng, address: String) {
        if (mLatLng.latitude != 0.0) {
            pickupLatLng = mLatLng
            pickupLocation = address
        }
    }

    fun getPickupAddress(pickuplatlng: LatLng) {
        if (pickuplatlng.latitude == 0.0) {
            findAddress(currentLatLng)
        } else {
            findAddress(pickuplatlng)
        }
    }


    private fun findAddress(mLatLng: LatLng) {
        if (mLatLng.latitude != 0.0) {
            if (!SessionSave.getSession(TaxiUtil.isNeedtoFetchAddress, activity, false)) {
                setDraggedAddress(mLatLng.latitude, mLatLng.longitude, resources.getString(R.string.pinlocation))
            } else {
                if (address != null)
                    address!!.cancel(true)
                if (address == null || address!!.status != AsyncTask.Status.PENDING && address!!.status != AsyncTask.Status.RUNNING) {
                    if (mLatLng.latitude != 0.0 && mLatLng.longitude != 0.0) {
                        addressRunning = true
                        address = AddressFromLatLng(activity, LatLng(mLatLng.latitude, mLatLng.longitude), this@MapFragment).execute()
                    }
                }
            }
        }
    }

    override fun getLatlngUpdates(lastLatLng: LatLng) {
        if (currentLatLng.latitude == 0.0) {
            currentLatLng = lastLatLng
            //to avoid tap drop until pickup location set initially
            addressRunning = true
            setDraggedAddress(0.0, 0.0, NC.getString(R.string.fetching_address))
            if (::map.isInitialized)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, zoom))
            if (::map.isInitialized)
                mapGpsInitialized(map, lastLatLng)
        } else {
            addressRunning = true
            if (::map.isInitialized)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, zoom))
        }
    }


    private fun zoomToLastKnownLatLng() {
        if (map != null && currentLatLng.latitude != 0.0 && !addressRunning)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoom))
        else
            getLastKnownLatLng(0)
    }


    private fun getDropAddress(dropLatLng: LatLng) {
        if (dropLatLng.latitude != 0.0) {
            findAddress(currentLatLng)
        }
    }


    private fun needTogetAddress() {
        if (pickupLatLng.latitude != 0.0 && currentLatLng.latitude != 0.0) {
            val dis = FloatArray(1)
            Location.distanceBetween(pickupLatLng.latitude, pickupLatLng.longitude, currentLatLng.latitude, currentLatLng.longitude, dis)
            if (dis[0] > 200 || pickupLocation.trim { it <= ' ' } == "" || pickupLocation == NC.getString(R.string.fetching_address)) {
                currentLatLng = pickupLatLng
                Log.v("distance_2001 ", "" + dis[0] + "__" + SessionSave.getSession(TaxiUtil.isNeedtoFetchAddress, activity, false))
                findAddress(pickupLatLng)
            }
        }
    }

}