package com.taximobility.service

import android.content.Context
import com.taximobility.data.apiData.ApiRequestData
import com.taximobility.data.apiData.CompanyDomainResponse
import com.taximobility.util.AppController
import com.taximobility.util.SessionSave
import com.taximobility.util.TaxiUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckCompanyDomain {

    fun callCheckCompanyDomain(context: Context) {
        val baseUrl = SessionSave.getSession(TaxiUtil.DOMAIN_URL, context)
        val client = AppController.getInstance().getCheckCompanyDomainapiManager(baseUrl)
        val request = ApiRequestData.BaseUrl()
        request.company_domain = SessionSave.getSession(TaxiUtil.ACCESS_KEY, context)
        request.company_main_domain = SessionSave.getSession(TaxiUtil.COMPANY_DOMAIN, context)
        request.device_type = "1"
        val response = client.callData(TaxiUtil.COMPANY_KEY, request)

        response.enqueue(object : Callback<CompanyDomainResponse> {

            override fun onResponse(call: Call<CompanyDomainResponse>, response: Response<CompanyDomainResponse>) {
                try {
                    if (response.isSuccessful) {
                        val cr = response.body()
                        if (cr!!.auth_key != null && cr.auth_key != "") {
                            SessionSave.saveSession(TaxiUtil.AUTH_KEY, cr.auth_key, context)
//                            context.startService(Intent(context, BackgroundCoreConfig::class.java))
                        } else {
//                            ShowToast.center(context, NC.getString(R.string.server_error))
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<CompanyDomainResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }
}