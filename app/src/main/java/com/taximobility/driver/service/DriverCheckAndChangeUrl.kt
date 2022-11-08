package com.taximobility.driver.service

import android.content.Context
import android.provider.Settings
import com.taximobility.driver.data.DriverCommonData
import com.taximobility.driver.utils.DriverSessionSave
import com.taximobility.util.AppController
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.atomic.AtomicInteger


open class CheckUrl {

    fun update(context: Context, newUrl: String, testUrl: String, urlFor: String) {
        val data = JSONObject()
        try {
            val c = AtomicInteger(0)

            var mUUID = ""
            if (DriverCommonData.mDevice_id == "") {
                if (UUID.randomUUID().toString() != "") {
                    mUUID = UUID.randomUUID().toString()
                } else {
                    mUUID = DriverCommonData.mDevice_id_constant + c.incrementAndGet()
                }
                DriverCommonData.mDevice_id = mUUID
            }
//            data.put("device_id", DriverCommonData.mDevice_id)

            data.put("device_id", Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID))
        } catch (e: JSONException) {
            e.printStackTrace()
        }

//        val client = NodeServiceGenerator(context, false, testUrl, 30).createService(CoreClient::class.java)
        val client = AppController.getInstance().getNodeApiManagerWithTimeOut_driver(testUrl, 30)

        val body = data.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val coreResponse = client.urlCheck(testUrl, body)
        coreResponse.enqueue(DriverRetrofitCallbackClass(context, object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val data: String?

                try {
                    try {

                        data = response.body()!!.string()
                        val json = JSONObject(data)
                        if (data != null && json.getString("status") == "1") {

                            DriverSessionSave.saveSession(urlFor, newUrl, context)
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        }))
    }
}