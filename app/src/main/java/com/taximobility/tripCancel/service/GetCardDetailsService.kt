package com.taximobility.tripCancel.service

import android.app.IntentService
import android.content.Intent
import com.taximobility.interfaces.APIResult
import com.taximobility.service.APIService_Retrofit_JSON
import com.taximobility.tripCancel.CreditCardData
import com.taximobility.tripCancel.CreditCardRepository
import com.taximobility.util.PASS_ID
import com.taximobility.util.SessionSave
import com.taximobility.util.TaxiUtil
import org.json.JSONException
import org.json.JSONObject

class GetCardDetailsService : IntentService("GetCardDetailsService") {
    private var creditCardRepository: CreditCardRepository? = null
    override fun onCreate() {
        super.onCreate()
        creditCardRepository = CreditCardRepository.getRepository(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        try {
            val j = JSONObject()
            j.put("passenger_id", SessionSave.getSession(PASS_ID, this))
            j.put("card_type", "")
            j.put("default", "")
            GetCardList("type=get_credit_card_details", j)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * GetCardlist class is used to get the passenger card details and update it into UI
     */
    private inner class GetCardList(string: String, data: JSONObject) : APIResult {
        init {
            APIService_Retrofit_JSON(this@GetCardDetailsService, this, data, false).execute(string)
        }

        override fun getResult(isSuccess: Boolean, result: String) {
            try {
                if (isSuccess) {
                    creditCardRepository?.deleteAllCards()
                    var name = ""
                    var id = ""
                    var type = ""
                    var month = ""
                    var year = ""
                    var card = ""
                    var cvv = ""
                    var default_card = ""
                    var original_cardno = ""
                    var original_cvv = ""
                    val json = JSONObject(result)
                    if (json.getInt("status") == 1) {

                        if (json.has(TaxiUtil.USER_WALLET_AMOUNT))
                            SessionSave.saveWalletAmount(json.getDouble(TaxiUtil.USER_WALLET_AMOUNT).toFloat(), this@GetCardDetailsService)
                        else
                            SessionSave.saveWalletAmount(0f, this@GetCardDetailsService)

                        val jarry = json.getJSONArray("detail")
                        val length = jarry.length()
                        for (i in 0 until length) {
                            id = jarry.getJSONObject(i).getString("passenger_cardid")
                            type = jarry.getJSONObject(i).getString("card_type")
                            month = jarry.getJSONObject(i).getString("expdatemonth")
                            year = jarry.getJSONObject(i).getString("expdateyear")
                            card = jarry.getJSONObject(i).getString("masked_creditcard_no")
                            cvv = jarry.getJSONObject(i).getString("masked_creditcard_cvv")
                            original_cardno = jarry.getJSONObject(i).getString("creditcard_no")
                            original_cvv = jarry.getJSONObject(i).getString("creditcard_cvv")
                            default_card = jarry.getJSONObject(i).getString("default_card")
                            name = jarry.getJSONObject(i).getString("card_holder_name")
                            val data = CreditCardData(name, id, type, month, year, card, cvv, default_card, original_cardno, original_cvv)
                            creditCardRepository?.insertCreditCard(data)
                        }
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
    }
}