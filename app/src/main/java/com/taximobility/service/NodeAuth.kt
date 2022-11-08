package com.taximobility.service

import android.content.Context
import com.taximobility.R
import com.taximobility.bookingmodule.BookTaxiHomeViewModel
import com.taximobility.features.CToast
import com.taximobility.interfaces.NodeAuthListener
import com.taximobility.util.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.util.*

open class NodeAuth {


    companion object {
        private var isAuthCallInProgress = false
        // For Singleton instantiation
        @Volatile
        private var instance: NodeAuth? = null

        private var nodeAuthListener: NodeAuthListener? = null
        lateinit var requestData: JSONObject
        lateinit var viewModel: BookTaxiHomeViewModel


        @JvmStatic
        fun getInstance(): NodeAuth {
            return instance ?: synchronized(this) {
                instance ?: NodeAuth().also { instance = it }
            }
        }
    }

    fun setListener(listener: NodeAuthListener, mRequestData: JSONObject, mViewModel: BookTaxiHomeViewModel) {
//        if(nodeAuthListener == null) {
            nodeAuthListener = listener
            requestData = mRequestData
            viewModel = mViewModel
//        }
    }

    fun getAuth(context: Context) {
        if (!isAuthCallInProgress) {
            setListenerForAuthResponse(false)
            if (NetworkStatus.isOnline(context)) {
                setListenerForAuthResponse(false)
                isAuthCallInProgress = true
                val data = JSONObject()
                try {
//                    data.put("device_id", Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID))
                    if (UUID.randomUUID().toString() != "") {
                        data.put("device_id", UUID.randomUUID().toString())
                    } else {
                        data.put("device_id", TaxiUtil.mDevice_id_constant)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
//                val client = NodeServiceGenerator(context, SessionSave.getSession(TaxiUtil.NODE_URL, context), 30).createService(CoreClient::class.java)
                val client = AppController.getInstance().getNodeApiManagerWithTimeOut(SessionSave.getSession(TaxiUtil.NODE_URL, context), 30)
                val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), data.toString())

                val coreResponse = client.nodeAuth(body)
                coreResponse.enqueue(RetrofitCallbackClass(context, object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                        val data: String?
                        isAuthCallInProgress = false
                        setListenerForAuthResponse(true)
                        if (response.isSuccessful) {
                            try {
                                if (response.body() != null) {
                                    data = response.body()!!.string()
                                    val json = JSONObject(data)
                                    SessionSave.saveSession(TaxiUtil.NODE_TOKEN, json.getString("token"), context)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                CToast.ShowToast(context, NC.getString(R.string.server_error))
                            }
                        } else
                            CToast.ShowToast(context, NC.getString(R.string.server_error))
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        isAuthCallInProgress = false
                        setListenerForAuthResponse(true)
                        t.printStackTrace()
                        CToast.ShowToast(context, NC.getString(R.string.server_error))
                    }
                }))
            } else {
                setListenerForAuthResponse(true)
                CToast.ShowToast(context, NC.getString(R.string.check_internet_connection))
            }
        } else {
            setListenerForAuthResponse(true)
        }
    }


    fun setListenerForAuthResponse(listener: Boolean) {
        if (nodeAuthListener != null)
            nodeAuthListener!!.nodeAuthListener(listener, requestData, viewModel)
    }
}