package com.taximobility.bookingmodule.Alert

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import com.taximobility.WebviewAct
import com.taximobility.bookingmodule.DriverLiveMovement.DriverLiveMove
import com.taximobility.interfaces.PackageClick
import com.google.android.gms.maps.model.LatLng
import org.json.JSONException
import org.json.JSONObject

class AlertPackagePlan(var packageClickListener:PackageClick) {

    lateinit var alertDialog: AlertDialog


    fun alertPackage(context: Activity, title: String, msg: String, successTxt: String, failureTxt: String, pickupLatLng: LatLng, dropLatLng: LatLng, pickLoc: String, dropLoc: String, driverLiveMovement: DriverLiveMove,requestType:String) {
        try {
//            alertDialog = Utility.alert_view_dialog(context, "" + title,
//                    "" + msg,
//                    "" + successTxt,
//                    "" + failureTxt,
//                    true, { dialog, which ->
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("latitude", pickupLatLng.latitude)
                    jsonObject.put("longitude", pickupLatLng.longitude)
                    jsonObject.put("drop_latitude", dropLatLng.latitude)
                    jsonObject.put("drop_longitude", dropLatLng.longitude)
                    jsonObject.put("pickupplace", pickLoc)
                    jsonObject.put("dropplace", dropLoc)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                val intent = Intent(context, WebviewAct::class.java)
                intent.putExtra("post_params", jsonObject.toString())
                intent.putExtra("type", "")
                context.startActivity(intent)
                driverLiveMovement.removeDriverLiveMovementCallback()
               // dialog.dismiss()
//            }, { dialog, which ->
//                if(requestType == "REQUEST"){
//                    packageClickListener.onPackageFailureClick()
//                }else {
//                    dialog.dismiss()
//                }}, "")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}