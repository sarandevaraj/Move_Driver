package com.moovex.driver.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.moovex.driver.DriverShowAlertAct
import com.moovex.driver.DriverSplashAct
import com.moovex.driver.DriverUserLoginAct
import com.moovex.driver.MainActivityDriver
import com.moovex.driver.data.DriverCommonData
import com.moovex.driver.data.DriverCommonData.AUTH_KEY
import com.moovex.driver.utils.DriverCToast
import com.moovex.driver.utils.DriverSessionSave
import com.moovex.util.TaxiUtil
import org.json.JSONObject

//Nodejs Log Code
//408				=>	Header not contains domains					(Need Alert)
//408				=>  DB Not connected,Site Config not retrived 	(Need Alert)
//408				=>  Timezone issue								(Need Alert)
//408				=>  Invalid Auth 								(Need Alert)
//408				=>  MongoDB down.
//408				=>  TryCatch Error								(Need Alert)
//
//Return data
//811				=>  Driver Get Current Information
//
//Message without action service stop only
//
//409				=>  Force to Update Build
//412				=>  Request to uninstall current build.
//
//Action with message move to login screen
//
//
//410				=>  Driver App Destory like as new build
//411				=>  Driver Logout
//
//Only action
//601 =>  Web Domain Url Change Request
//602 =>  Node Url Change Request
//603 =>  Token Expired

class DriverCheckStatus(val json: JSONObject, val context: Context) {

    fun isNormal(): Boolean {
        val normal: Boolean

        if (json.has("token"))
            DriverSessionSave.saveSession(
                DriverCommonData.NODE_TOKEN,
                json.getString("token"),
                context
            )

        var statusCode = 0
        if (json.has("status"))
            statusCode = json.getInt("status")

        var message: String? = null
        if (json.has("message")) {
            message = json.getString("message")
        }


        when (statusCode) {
            408 -> {
                normal = false
                Handler(Looper.getMainLooper()).post { DriverCToast.ShowToast(context, message); }

            }
            601 -> {
                normal = false
//                SessionSave.saveSession("base_url", message, context)
                message?.let {
                    CheckUrl().update(
                        context,
                        json.getString("domain"),
                        it,
                        "base_url"
                    )
                }
            }
            602 -> {
                normal = false
//                SessionSave.saveSession(CommonData.NODE_URL, message, context)
                message?.let {
                    CheckUrl().update(
                        context,
                        json.getString("domain"),
                        it,
                        DriverCommonData.NODE_URL
                    )
                }

            }
            603 -> {
                normal = false
                DriverSessionSave.saveSession(DriverCommonData.NODE_TOKEN, "0", context)
                DriverNodeAuth.getInstance().getAuth(context)
            }

            409 -> {
                normal = false
                val cancelIntent = Intent()
                val bun = Bundle()
                bun.putString("message", message ?: "")
                bun.putBoolean("move_to_playstore", true)
                cancelIntent.putExtras(bun)
                cancelIntent.action = Intent.ACTION_MAIN
                cancelIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                cancelIntent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_SINGLE_TOP
                val cn = ComponentName(context, DriverShowAlertAct::class.java)
                cancelIntent.component = cn
                context.stopService(Intent(context, LocationUpdate::class.java))
                context.startActivity(cancelIntent)
            }

            410 -> {
                normal = false
                DriverSessionSave.clearAllSession(context)
                forceLogout("")
                val cancelIntent = Intent()
                val bun = Bundle()
                bun.putString("alert_message", message ?: "")
                cancelIntent.putExtras(bun)
                cancelIntent.action = Intent.ACTION_MAIN
                cancelIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                cancelIntent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK /*or Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK*/
                val cn = ComponentName(context, DriverSplashAct::class.java)
                cancelIntent.component = cn
                context.startActivity(cancelIntent)
            }

            412 -> {
                normal = false
                val cancelIntent = Intent()
                val bun = Bundle()
                bun.putBoolean("move_to_playstore", false)
                bun.putString("message", message ?: "")
                cancelIntent.putExtras(bun)
                cancelIntent.action = Intent.ACTION_MAIN
                cancelIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                cancelIntent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_SINGLE_TOP
                val cn = ComponentName(context, DriverShowAlertAct::class.java)
                cancelIntent.component = cn
                context.stopService(Intent(context, LocationUpdate::class.java))
                context.startActivity(cancelIntent)
            }

            411 -> {
                normal = false
                MainActivityDriver.clearsession(context)
                val cancelIntent = Intent()
                val bun = Bundle()
                bun.putString("alert_message", message ?: "")
                cancelIntent.putExtras(bun)
                cancelIntent.action = Intent.ACTION_MAIN
                cancelIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                cancelIntent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK /*or Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_SINGLE_TOP*/
                val cn = ComponentName(context, DriverUserLoginAct::class.java)
                cancelIntent.component = cn
                context.startActivity(cancelIntent)
            }

            811 -> {
                normal = false
//                SendDriverDeviceInfo().sendInfo(context, "-1")
            }
            else -> normal = true
        }
        return normal
    }


    /**
     * Method to logout user if status -101 and redirect to login page
     * @param message - To intimate user by showing alert message
     */
    private fun forceLogout(message: String) {
//        CToast.ShowToast(context, message)
        DriverServiceGenerator.API_BASE_URL = ""
        DriverSessionSave.saveSession("base_url", "", context)
        DriverSessionSave.saveSession("Id", "", context)
        DriverSessionSave.clearAllSession(context)
        DriverSessionSave.saveSession(DriverCommonData.GETCORE_LASTUPDATE, "", context)
        context.stopService(Intent(context, LocationUpdate::class.java))
//        context.stopService(Intent(context, WaitingTimerRun::class.java))
    }

    fun updateAuthKey() {
        if (json.has("auth_key")) {
            val authKey = json.getString(AUTH_KEY)
            if (authKey != null && authKey != "")
                DriverSessionSave.saveSession(AUTH_KEY, authKey, context)
        }

        if (json.has(DriverCommonData.USER_KEY)) {
            if (json.getString(DriverCommonData.USER_KEY) != "" && json.getString(DriverCommonData.USER_KEY) != null)
                DriverSessionSave.saveSession(
                    DriverCommonData.USER_KEY,
                    json.getString(DriverCommonData.USER_KEY),
                    context
                )
            println("AUTH_KEY_USER_KEY" + " " + json.getString(TaxiUtil.USER_KEY))

        }

        var statusCode = ""
        if (json.has("status"))
            statusCode = json.getString("status")
        var message = ""
        if (json.has("message")) {
            message = json.getString("message")
        }
        when (statusCode) {
            "296" -> {
                handlingInvalidToken(message)
            }
            "128" -> {
                handlingInvalidUserKey(message)
            }
        }
    }

    private fun handlingInvalidToken(message: String) {
        clearSession(context)
        if (message != "") {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                DriverCToast.ShowToast(context, message)
            }
        }
        DriverSessionSave.saveSession(AUTH_KEY, "", context)
        DriverCheckCompanyDomain().callCheckCompanyDomain(context)
        val cancelIntent = Intent()
        cancelIntent.action = Intent.ACTION_MAIN
        cancelIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        cancelIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK /*or Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK*/
        val cn = ComponentName(context, DriverUserLoginAct::class.java)
        cancelIntent.component = cn
        context.startActivity(cancelIntent)
    }

    private fun handlingInvalidUserKey(message: String) {
        clearSession(context)
        if (message != "") {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                DriverCToast.ShowToast(context, message)
            }
        }
        DriverSessionSave.saveSession(DriverCommonData.USER_KEY, "", context)

        val cancelIntent = Intent()
        cancelIntent.action = Intent.ACTION_MAIN
        cancelIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        cancelIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK /*or Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK*/
        val cn = ComponentName(context, DriverUserLoginAct::class.java)
        cancelIntent.component = cn
        context.startActivity(cancelIntent)
    }

    private fun clearSession(ctx: Context) {

        try {
            DriverSessionSave.saveSession("status", "", ctx)
            DriverSessionSave.saveSession("Id", "", ctx)
            DriverSessionSave.saveSession("Driver_locations", "", ctx)
            DriverSessionSave.saveSession("driver_id", "", ctx)
            DriverSessionSave.saveSession("Name", "", ctx)
            DriverSessionSave.saveSession("company_id", "", ctx)
            DriverSessionSave.saveSession("bookedby", "", ctx)
            DriverSessionSave.saveSession("p_image", "", ctx)
            DriverSessionSave.saveSession("Email", "", ctx)
            DriverSessionSave.saveSession("phone_number", "", ctx)
            DriverSessionSave.saveSession("driver_password", "", ctx)
            DriverSessionSave.saveSession("trip_id", "", ctx)
            DriverSessionSave.setWaitingTime(0L, ctx)

        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }

    }
}