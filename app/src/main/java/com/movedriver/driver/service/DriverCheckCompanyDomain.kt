package com.movedriver.driver.service

import android.content.Context
import com.movedriver.driver.data.DriverCommonData
import com.movedriver.driver.data.apiData.DriverApiRequestData
import com.movedriver.driver.data.apiData.DriverCompanyDomainResponse
import com.movedriver.driver.utils.DriverSessionSave
import com.movedriver.util.AppController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DriverCheckCompanyDomain {

    fun callCheckCompanyDomain(context: Context) {
        val baseUrl = DriverSessionSave.getSession(DriverCommonData.DOMAIN_URL, context)
        val client = AppController.getInstance().getCheckCompanyDomainapiManager_driver(baseUrl)
        val request = DriverApiRequestData.BaseUrl()
        request.company_domain = DriverSessionSave.getSession(DriverCommonData.ACCESS_KEY, context)
        request.company_main_domain =
            DriverSessionSave.getSession(DriverCommonData.COMPANY_DOMAIN, context)
        request.device_type = "1"
        val response = client.callData(DriverServiceGenerator.COMPANY_KEY, request)

        response.enqueue(object : Callback<DriverCompanyDomainResponse> {

            override fun onResponse(
                call: Call<DriverCompanyDomainResponse>,
                response: Response<DriverCompanyDomainResponse>
            ) {
                try {
                    if (response.isSuccessful) {
                        val cr = response.body()
                        if (cr!!.auth_key != "" && cr.auth_key != null) {
                            DriverSessionSave.saveSession(
                                DriverCommonData.AUTH_KEY, cr.auth_key, context
                            )
                        } /*else {
//                            CToast.ShowToast(context, NC.getString(R.string.server_error))
                        }*/
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<DriverCompanyDomainResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}