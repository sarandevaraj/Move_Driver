package com.taximobility.driver.service

import android.content.Context
import android.provider.Settings
import com.taximobility.R
import com.taximobility.driver.data.DriverCommonData
import com.taximobility.driver.utils.DriverCToast
import com.taximobility.driver.utils.DriverNC
import com.taximobility.driver.utils.DriverNetworkStatus
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

class DriverNodeAuth private constructor() {

    companion object {
        private var isAuthCallInProgress = false

        // For Singleton instantiation
        @Volatile
        private var instance: DriverNodeAuth? = null

        @JvmStatic
        fun getInstance(): DriverNodeAuth {
            return instance ?: synchronized(this) {
                instance ?: DriverNodeAuth().also { instance = it }
            }
        }
    }

    fun getAuth(context: Context) {
        if (!isAuthCallInProgress) {
            if (DriverNetworkStatus.isOnline(context)) {
                isAuthCallInProgress = true
                val data = JSONObject()
                try {
                    /*if (UUID.randomUUID().toString() != "") {
                        data.put("device_id", UUID.randomUUID().toString())
                    } else {
                        data.put("device_id", DriverCommonData.mDevice_id_constant)
                    }*/
                    data.put(
                        "device_id", Settings.Secure.getString(
                            context.contentResolver, Settings.Secure.ANDROID_ID
                        )
                    )
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

//                val client = NodeServiceGenerator(context, false, SessionSave.getSession(CommonData.NODE_URL, context), 30).createService(CoreClient::class.java)
                val client = AppController.getInstance().getNodeApiManagerWithTimeOut_driver(
                    DriverSessionSave.getSession(
                        DriverCommonData.DRIVER_NODE_URL, context
                    ), 30
                )

                val body = data.toString()
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val coreResponse = client.nodeAuth(body)
                coreResponse.enqueue(
                    DriverRetrofitCallbackClass(context, object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>, response: Response<ResponseBody>
                        ) {
                            val data: String?
                            isAuthCallInProgress = false

                            if (response.isSuccessful) {
                                try {
                                    if (response.body() != null) {
                                        data = response.body()!!.string()
                                        val json = JSONObject(data)
                                        DriverSessionSave.saveSession(
                                            DriverCommonData.NODE_TOKEN,
                                            json.getString("token"),
                                            context
                                        )
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    DriverCToast.ShowToast(
                                        context, DriverNC.getString(R.string.server_error)
                                    )
                                }
                            } else DriverCToast.ShowToast(
                                context, DriverNC.getString(R.string.server_error)
                            )

                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            t.printStackTrace()
                            isAuthCallInProgress = false
                            DriverCToast.ShowToast(
                                context, DriverNC.getString(R.string.server_error)
                            )
                        }
                    })
                )
            } else {
                DriverCToast.ShowToast(context, DriverNC.getString(R.string.check_net_connection))
            }
        }
    }

}