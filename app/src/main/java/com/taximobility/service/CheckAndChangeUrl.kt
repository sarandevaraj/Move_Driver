package com.taximobility.service

import android.content.Context
import com.taximobility.util.AppController
import com.taximobility.util.SessionSave
import com.taximobility.util.TaxiUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.atomic.AtomicInteger


object CheckUrl {

    fun update(context: Context, newUrl: String, testUrl: String, urlFor: String) {
        val data = JSONObject()
        try {
//            data.put("device_id", Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID))
          /*  if (UUID.randomUUID().toString().isNotEmpty()) {
                data.put("device_id", UUID.randomUUID().toString())
            } else {
                data.put("device_id",TaxiUtil.mDevice_id_constant)
            }*/
            val c = AtomicInteger(0)

            var mUUID = ""
            if (TaxiUtil.mDevice_id == "") {
                if (UUID.randomUUID().toString() != "") {
                    mUUID = UUID.randomUUID().toString()
                } else {
                    mUUID = TaxiUtil.mDevice_id_constant + c.incrementAndGet()
                }
                TaxiUtil.mDevice_id = mUUID
            }
            data.put("device_id", TaxiUtil.mDevice_id)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

//        val client = NodeServiceGenerator(context, testUrl, 30).createService(CoreClient::class.java)
        val client = AppController.getInstance().getNodeApiManagerWithTimeOut(testUrl, 30)
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), data.toString())

        val coreResponse = client.urlCheck(testUrl, body)
        coreResponse.enqueue(RetrofitCallbackClass(context, object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val data: String?
                if (response.isSuccessful) {
                    try {
                        if (response.body() != null) {
                            data = response.body()!!.string()
                            if (data != null) {
                                val json = JSONObject(data)
                                if (json.getString("status") == "1")
                                    SessionSave.saveSession(urlFor, newUrl, context)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        }))
    }
}