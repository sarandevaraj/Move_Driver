package com.taximobility.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.taximobility.ShowAlertAct
import com.taximobility.SplashActivity
import com.taximobility.bookingmodule.BookTaxiHomeRepository
import com.taximobility.driver.DriverUserLoginAct
import com.taximobility.features.CToast
import com.taximobility.tripCancel.CreditCardRepository
import com.taximobility.util.*
import com.taximobility.util.TaxiUtil.AUTH_KEY
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

class CheckStatus(val json: JSONObject, val context: Context) {

    fun isNormal(): Boolean {
        val normal: Boolean

        if (json.has("token"))
            SessionSave.saveSession(TaxiUtil.NODE_TOKEN, json.getString("token"), context)

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
                Handler(Looper.getMainLooper()).post { CToast.ShowToast(context, message); }

            }
            601 -> {
                normal = false
//                SessionSave.saveSession("base_url", message, context)
                message?.run {
                    CheckUrl.update(context, json.getString("domain"), this, "base_url")
                }
            }
            602 -> {
                normal = false
//                SessionSave.saveSession(CommonData.NODE_URL, message, context)
                message?.run {
                    CheckUrl.update(context, json.getString("domain"), this, TaxiUtil.NODE_URL)
                }

            }
            603 -> {
                normal = false
                SessionSave.saveSession(TaxiUtil.NODE_TOKEN, "", context)
                NodeAuth.getInstance().getAuth(context)
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
                cancelIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val cn = ComponentName(context, ShowAlertAct::class.java)
                cancelIntent.component = cn
                context.startActivity(cancelIntent)
            }
            410 -> {
                context.stopService(Intent(context, GetPassengerUpdate::class.java))
                normal = false
                SessionSave.clearAllSession(context)
                forceLogout("")
                val cancelIntent = Intent()
                val bun = Bundle()
                bun.putString("alert_message", message ?: "")
                cancelIntent.putExtras(bun)
                cancelIntent.action = Intent.ACTION_MAIN
                cancelIntent.addCategory(Intent.CATEGORY_LAUNCHER)
//                cancelIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_SINGLE_TOP
                cancelIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val cn = ComponentName(context, SplashActivity::class.java)
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
                cancelIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val cn = ComponentName(context, ShowAlertAct::class.java)
                cancelIntent.component = cn
                context.startActivity(cancelIntent)
            }

            411 -> {
                normal = false
                context.stopService(Intent(context, GetPassengerUpdate::class.java))

                SessionSave.saveSession(PASS_ID, "", context)
                val cancelIntent = Intent()
                val bun = Bundle()
                bun.putString("message", message ?: "")
                cancelIntent.action = Intent.ACTION_MAIN
                cancelIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                cancelIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val cn = ComponentName(context, DriverUserLoginAct::class.java)
                cancelIntent.component = cn
                cancelIntent.putExtra("alert_message", message ?: "")
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
        SessionSave.saveSession("base_url", "", context)
        SessionSave.saveSession(PASS_ID, "", context)
        SessionSave.clearAllSession(context)
    }


    fun updateAuthKey() {
        if (json.has("auth_key")) {
            if (json.getString(AUTH_KEY) != "" && json.getString(AUTH_KEY) != null)
                println("AUTH_KEY"+" "+ json.getString(AUTH_KEY) )
                SessionSave.saveSession(AUTH_KEY, json.getString(AUTH_KEY), context)
        }
        if (json.has(TaxiUtil.USER_KEY)) {
            if (json.getString(TaxiUtil.USER_KEY) != "" && json.getString(TaxiUtil.USER_KEY) != null)
                SessionSave.saveSession(TaxiUtil.USER_KEY, json.getString(TaxiUtil.USER_KEY), context)
            println("AUTH_KEY_USER_KEY"+" "+ json.getString(TaxiUtil.USER_KEY) )

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
                handlingInvalidUserToken(message)
            }
        }
    }

    private fun handlingInvalidToken(message: String) {
        SessionSave.saveWalletAmount(0f, context)
        SessionSave.saveSession(AUTH_KEY, "", context)
        clearSession(context)
        if (message != "") {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                CToast.ShowToast(context, message)
            }
        }
        CheckCompanyDomain().callCheckCompanyDomain(context)
        val logIn = Intent(context, DriverUserLoginAct::class.java)
        logIn.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(logIn)
        val creditCardRepository = CreditCardRepository.getRepository(context)
        val placesRepository = BookTaxiHomeRepository(context)
        creditCardRepository?.deleteAllCards()
        placesRepository.deleteSavedPlaces()
    }


    private fun handlingInvalidUserToken(message: String) {
        SessionSave.saveWalletAmount(0f, context)
        SessionSave.saveSession(TaxiUtil.USER_KEY, "", context)
        clearSession(context)
        if (message != "") {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                CToast.ShowToast(context, message)
            }
        }
        val logIn = Intent(context, DriverUserLoginAct::class.java)
        logIn.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(logIn)
        val creditCardRepository = CreditCardRepository.getRepository(context)
        val placesRepository = BookTaxiHomeRepository(context)
        creditCardRepository?.deleteAllCards()
        placesRepository.deleteSavedPlaces()
    }

    private fun clearSession(ctx: Context) {

        try {
            SessionSave.saveSession("TaxiStatus", "", ctx)
            SessionSave.saveSession(LOGOUT, "", ctx)
            SessionSave.saveSession(PASS_ID, "", ctx)
            SessionSave.saveSession("service_type_name", "", ctx)
            SessionSave.saveSession("AdminMail", "", ctx)
            SessionSave.saveSession("Email", "", ctx)
            SessionSave.saveSession(PASS_TRIP_ID, "", ctx)
            SessionSave.saveSession("Register", "", ctx)
            SessionSave.saveSession("PLAT", "", ctx)
            SessionSave.saveSession("PLNG", "", ctx)
            SessionSave.saveSession("service_type", "", ctx)
            SessionSave.saveSession("NotifyMessage", "", ctx)
            SessionSave.saveSession("Server_Response", "", ctx)
            SessionSave.saveSession("Server_bookinglist", "", ctx)
            SessionSave.saveSession("trip_id", "", ctx)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}