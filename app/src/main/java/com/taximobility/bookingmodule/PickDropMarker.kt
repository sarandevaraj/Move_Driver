package com.taximobility.bookingmodule

import android.app.Activity
import android.os.Handler
import android.widget.LinearLayout
import com.taximobility.R
import com.taximobility.util.CustomMarker
import com.taximobility.util.TaxiUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

object PickDropMarker {

    private var dropMarker: Marker? = null
    private var pickupMarker: Marker? = null

    private var FREE_TO_MOVE: Boolean = true

    fun setPickMarker(map: GoogleMap, pickupLatLng: LatLng) {
        if (pickupLatLng.latitude != 0.0) {
            if (pickupMarker != null)
                pickupMarker!!.remove()
            pickupMarker = map.addMarker(MarkerOptions()
                    .position(pickupLatLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup_dot)))
        }
    }

    fun setDropMarker(map: GoogleMap, dropLatLng: LatLng) {
        if (dropLatLng.latitude != 0.0) {
            if (dropMarker != null)
                dropMarker!!.remove()
            dropMarker = map.addMarker(MarkerOptions()
                    .position(dropLatLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.drop_dot)))
        }
    }

    fun setPickMarkerWithCustomView(activity: Activity, map: GoogleMap, pickLoc: String, pickupLatLng: LatLng, ETime: Double) {
        if (pickupLatLng.latitude != 0.0) {
            var eTime = ETime
            if (pickupMarker != null)
                pickupMarker!!.remove()
            if (TaxiUtil.mDriverdata.size > 0) {
                if (eTime == 0.0) {
                    eTime = 1.0
                }
            }
            val b = CustomMarker.getMarkerBitmapFromView(eTime.toInt().toString(), activity, pickLoc)
            try {
                pickupMarker = map.addMarker(MarkerOptions()
                        .position(pickupLatLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(b)))
                pickupMarker?.run {
                    tag = "pickup"
                    setAnchor(0.0f, 1f)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun setDropMarkerWithCustomView(activity: Activity, map: GoogleMap, dropLoc: String, dropLatLng: LatLng) {

        if (dropLatLng.latitude != 0.0) {
            if (dropMarker != null)
                dropMarker!!.remove()
            dropMarker = map.addMarker(MarkerOptions()
                    .position(dropLatLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(CustomMarker.getMarkerBitmapFromViewForDrop(dropLoc, activity))))
            dropMarker?.run {
                tag = "dropMarker"
                setAnchor(1.0f, 1f)
            }

        }
    }


    fun moveCamera(activity: Activity, map: GoogleMap, bottomViewLay: LinearLayout) {
        try {
            val builder = LatLngBounds.Builder()
            if (pickupMarker != null)
                builder.include(pickupMarker!!.position)
            if (dropMarker != null)
                builder.include(dropMarker!!.position)
            val bounds = builder.build()
            val width = BookTaxiHomePage.displayWidth
            val padding = (width * 0.15).toInt() // offset from edges of the map 10% of screen
            bottomViewLay.post {
                try {
                    val adjustHeight = BookTaxiHomePage.displayHeight - bottomViewLay.height
                    val cu = CameraUpdateFactory.newLatLngBounds(bounds, width, adjustHeight, padding)
                    if (FREE_TO_MOVE) {
                        Handler().postDelayed({ FREE_TO_MOVE = true }, 3000)
                        map.moveCamera(cu)
                        if (pickupMarker != null && map.projection.toScreenLocation(pickupMarker!!.position).x > width / 2) {
                            updateCameraBearing(map)
                        } else {
                            resetCameraBearing(map)
                        }
                        FREE_TO_MOVE = false
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun updateCameraBearing(mMap: GoogleMap) {
        val camPos = CameraPosition
                .builder(
                        mMap.cameraPosition
                )
                .bearing(200f)
                .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos))
    }

    private fun resetCameraBearing(mMap: GoogleMap) {
        val camPos = CameraPosition
                .builder(mMap.cameraPosition
                )
                .bearing(0f)
                .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos))
    }


}