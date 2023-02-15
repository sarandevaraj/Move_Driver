package com.movedriver.driver

import android.annotation.SuppressLint
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.movedriver.R
import com.movedriver.driver.adapter.DriverPendingHistoryAdapter
import com.movedriver.driver.data.apiData.DriverApiRequestData
import com.movedriver.driver.data.apiData.DriverSettlementPaymentData
import com.movedriver.driver.data.apiData.DriverSettlementReqData
import com.movedriver.driver.service.DriverRetrofitCallbackClass
import com.movedriver.driver.utils.DirverColorchange
import com.movedriver.driver.utils.DriverDatePicker_CardExpiry
import com.movedriver.driver.utils.DriverNC
import com.movedriver.driver.utils.DriverSessionSave
import com.movedriver.util.AppController
import kotlinx.android.synthetic.main.driver_include_headler.*
import kotlinx.android.synthetic.main.driver_settlement_lay.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DriverSettlementDetail : DriverBaseActivity(), DriverDatePicker_CardExpiry.DialogInterface {

    private var editNameDialog: DriverDatePicker_CardExpiry? = null

    private var mMonth: Int = 0

    private var mDay: Int = 0

    private var mYear: Int = 0

    private var datePick: Boolean = false

    private var fromDate: String = ""

    private var toDate: String = ""

    private var datePic: String = ""

    private val args = Bundle()

    var infoObj: DriverSettlementReqData.Info? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.driver_settlement_lay)
        DirverColorchange.ChangeColor(
            (this@DriverSettlementDetail.findViewById(android.R.id.content) as ViewGroup).getChildAt(
                0
            ) as ViewGroup, this@DriverSettlementDetail
        )

        args.putString("KEY", "0")

        leftIcon.visibility = View.VISIBLE

        header_titleTxt.text = DriverNC.getString(R.string.settlement_request)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

        toDate = dateFormat.format(Date().time)

        val calendar = Calendar.getInstance()

        calendar.add(Calendar.DAY_OF_YEAR, -7)

        fromDate = dateFormat.format(calendar.time)

        from_txt.text = fromDate

        to_txt.text = toDate

        callRequestApi("", "")
    }

    override fun onResume() {

        super.onResume()

        txt_header_amount.text =
            DriverNC.getString(R.string.amount) + "(" + DriverSessionSave.getSession(
                "site_currency", this@DriverSettlementDetail
            ).trim() + ")"

        leftIcon.setOnClickListener {
            finish()
        }

        from_txt_lay.setOnClickListener {

            datePick = true

            from_txt_lay.isClickable = false

            val fm = supportFragmentManager

            editNameDialog = DriverDatePicker_CardExpiry()

            args.putString("selected_date", from_txt.text.toString())

            editNameDialog?.arguments = args

            editNameDialog?.show(fm, "fragment_edit_name")

        }

        to_txt_lay.setOnClickListener {

            datePick = false

            to_txt_lay.isClickable = false

            val fm = supportFragmentManager

            editNameDialog = DriverDatePicker_CardExpiry()

            args.putString("selected_date", to_txt.text.toString())

            editNameDialog?.arguments = args

            editNameDialog?.show(fm, "fragment_edit_name")

        }

        btn_go.setOnClickListener {

            val dateStr = from_txt.text.toString()

            val dateStr2 = to_txt.text.toString()

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

            val startDateValue = sdf.parse(dateStr)

            val endDateValue = sdf.parse(dateStr2)

            val diff = endDateValue!!.time - startDateValue!!.time

            val numberOfDays: Int = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()

            println("Days: $numberOfDays")

            if (numberOfDays > 31) {

                val builder = AlertDialog.Builder(this@DriverSettlementDetail)

                builder.setTitle("")

                builder.setMessage(DriverNC.getString(R.string.days_limit_exceed))

                builder.setPositiveButton(DriverNC.getString(R.string.t_ok)) { dialog, which ->

                    dialog.dismiss()

                }

                val dialogs: AlertDialog = builder.create()

                dialogs.setOnShowListener {

                    if (dialogs != null) {

                        dialogs.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(this@DriverSettlementDetail.resources.getColor(R.color.button_accept))

                        dialogs.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(this@DriverSettlementDetail.resources.getColor(R.color.black))

                    }

                }

                dialogs.show()

            } else {

                callRequestApi(dateStr, dateStr2)

            }

        }

        txt_req_admin.setOnClickListener {

            callPaymentApi()

        }

    }

    private fun callPaymentApi() {

        showLoading()

//        val client = ServiceGenerator(this@SettlementDetail, false).createService(CoreClient::class.java)

        val client = AppController.getInstance().apiManagerWithEncryptBaseUrl_driver
        val req = DriverApiRequestData.PaymentReq()

        req.driver_id = DriverSessionSave.getSession("Id", this@DriverSettlementDetail)

        req.comments = ""

        req.info = infoObj

        val call = client.settlement_paymentCall(
            req, DriverSessionSave.getSession("Lang", this@DriverSettlementDetail)
        )

        showLoading()

        call.enqueue(
            DriverRetrofitCallbackClass(this@DriverSettlementDetail,
                object : Callback<DriverSettlementPaymentData> {

                    override fun onResponse(
                        call: Call<DriverSettlementPaymentData>,
                        response: Response<DriverSettlementPaymentData>
                    ) {

                        cancelLoading()

                        if (response.isSuccessful) {

                            val data = response.body()

                            val builder = AlertDialog.Builder(this@DriverSettlementDetail)

                            builder.setTitle("")

                            builder.setMessage(data?.message)

                            builder.setPositiveButton(DriverNC.getString(R.string.ok)) { dialog, which ->

                                dialog.dismiss()

                                if (data?.status == 1) {

                                    startActivity(
                                        Intent(
                                            this@DriverSettlementDetail,
                                            SettlementHistoryActivityDriver::class.java
                                        )
                                    )

                                }

                            }

                            builder.setCancelable(false)

                            val dialogs: AlertDialog = builder.create()

                            dialogs.setOnShowListener {

                                if (dialogs != null) {

                                    dialogs.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                                        this@DriverSettlementDetail.resources.getColor(R.color.button_accept)
                                    )

                                    dialogs.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                                        this@DriverSettlementDetail.resources.getColor(R.color.black)
                                    )

                                }

                            }

                            dialogs.show()

                        }

                    }

                    override fun onFailure(call: Call<DriverSettlementPaymentData>, t: Throwable) {

                        cancelLoading()

                        t.printStackTrace()

                    }

                })
        )

    }

    private fun showLoading() {

        showProgress.visibility = View.VISIBLE

        root_layout.visibility = View.GONE

    }

    private fun cancelLoading() {

        showProgress.visibility = View.GONE

        root_layout.visibility = View.VISIBLE

    }

    private fun callRequestApi(from: String, to: String) {

        showLoading()

//        val client = ServiceGenerator(this@SettlementDetail, false).createService(CoreClient::class.java)
        val client = AppController.getInstance().apiManagerWithEncryptBaseUrl_driver
        val req = DriverApiRequestData.SettlementReq()

        req.driver_id = DriverSessionSave.getSession("Id", this@DriverSettlementDetail)

        req.start_date = from

        req.end_date = to

        val call = client.settlement_reqCall(
            req, DriverSessionSave.getSession("Lang", this@DriverSettlementDetail)
        )

        call.enqueue(
            DriverRetrofitCallbackClass(this@DriverSettlementDetail,
                object : Callback<DriverSettlementReqData> {


                    @SuppressLint("WrongConstant")
                    override fun onResponse(
                        call: Call<DriverSettlementReqData>,
                        response: Response<DriverSettlementReqData>
                    ) {

                        cancelLoading()

                        val settlementData = response.body()

                        if (response.isSuccessful) {

                            if (settlementData?.status == 1) {

                                layout_content.visibility = View.VISIBLE

                                txt_nodata.visibility = View.GONE

                                infoObj = settlementData.info

                                txt_req.text = settlementData.details?.hints?.replace(
                                    "#AMOUNT".toRegex(), DriverSessionSave.getSession(
                                        "site_currency", this@DriverSettlementDetail
                                    ) + "" + settlementData.details?.total_amount_driver
                                )

                                if (settlementData.details.start_date != null) {

                                    from_txt.text =
                                        getDate(settlementData.details.start_date.toLong())

                                } else {

                                    from_txt.text = fromDate

                                }

                                if (settlementData.details.end_date != null) {

                                    to_txt.text = getDate(settlementData.details.end_date.toLong())

                                } else {

                                    to_txt.text = toDate

                                }

                                tv_tax_value.text = DriverSessionSave.getSession(
                                    "site_currency", this@DriverSettlementDetail
                                ) + "" + settlementData.details.tax

                                txt_total_val.text = DriverSessionSave.getSession(
                                    "site_currency", this@DriverSettlementDetail
                                ) + "" + settlementData.details?.total_earning
                                2
                                txt_net_earnings_val.text = DriverSessionSave.getSession(
                                    "site_currency", this@DriverSettlementDetail
                                ) + "" + settlementData.details?.wallet_amount

                                txt_cash_val.text = DriverSessionSave.getSession(
                                    "site_currency", this@DriverSettlementDetail
                                ) + "" + settlementData.details?.cash_collected

                                txt_card_val.text = DriverSessionSave.getSession(
                                    "site_currency", this@DriverSettlementDetail
                                ) + "" + settlementData.details?.card_payment

                                txt_admin.text = "" + settlementData.details?.settlement_type

                                txt_admin_value.text = DriverSessionSave.getSession(
                                    "site_currency", this@DriverSettlementDetail
                                ) + "" + settlementData.details?.total_amount_driver

                                txt_req_sent.text =
                                    DriverNC.getString(R.string.last_req) + settlementData.details?.last_request_date

                                tv_drivercommission_value.text = DriverSessionSave.getSession(
                                    "site_currency", this@DriverSettlementDetail
                                ) + "" + settlementData.details?.driver_commission_amount

                                val adminCommission: Float? =
                                    settlementData.details?.admin_commission_amount?.toFloat()

                                if (adminCommission != null && adminCommission > 0) {

                                    card_view.visibility = View.VISIBLE

                                    admincommission_lay.visibility = View.VISIBLE

                                    tv_admincommission_value.text = DriverSessionSave.getSession(
                                        "site_currency", this@DriverSettlementDetail
                                    ) + "" + settlementData.details?.admin_commission_amount

                                } else {

                                    card_view.visibility = View.GONE

                                    admincommission_lay.visibility = View.GONE

                                }

                                if (settlementData.details.list != null && settlementData.details.list.size > 0) {

                                    menu_header_lay.visibility = View.VISIBLE

                                    txt_request_payment.visibility = View.VISIBLE

                                    val mAdapter = DriverPendingHistoryAdapter(
                                        this@DriverSettlementDetail, settlementData.details?.list
                                    )

                                    rv_settlement.layoutManager = LinearLayoutManager(
                                        this@DriverSettlementDetail, LinearLayout.VERTICAL, false
                                    )

                                    rv_settlement.adapter = mAdapter

                                } else {

                                    menu_header_lay.visibility = View.GONE

                                    txt_request_payment.visibility = View.GONE

                                }

                                if (settlementData.details.show_button == 1) {

                                    txt_req_admin.visibility = View.VISIBLE

                                    txt_req_sent.visibility = View.VISIBLE

                                } else {

                                    txt_req_admin.visibility = View.GONE

                                    txt_req_sent.visibility = View.GONE

                                }

                            } else {

                                layout_content.visibility = View.GONE

                                txt_nodata.visibility = View.VISIBLE

                                txt_nodata.text = settlementData?.message

                            }

                        } else {

                            layout_content.visibility = View.GONE

                            txt_nodata.visibility = View.VISIBLE

                            txt_nodata.text = DriverNC.getString(R.string.please_check_internet)

                        }

                    }

                    override fun onFailure(call: Call<DriverSettlementReqData>, t: Throwable) {

                        t.printStackTrace()

                        cancelLoading()

                        layout_content.visibility = View.GONE

                        txt_nodata.visibility = View.VISIBLE

                        txt_nodata.text = DriverNC.getString(R.string.please_check_internet)

                    }

                })
        )

    }

    override fun onSuccess(monthOfYear: Int, year: Int, day: Int) {

        mYear = year

        mMonth = monthOfYear + 1

        mDay = 2

        editNameDialog?.dismiss()

        if (datePick) {

            from_txt_lay.isClickable = true

            datePic =
                StringBuilder().append(mYear).append("-").append(checkDigit(mMonth)).append("-")
                    .append(checkDigit(day)).append(" ").toString()

            if (checkDateBefore(to_txt.text.toString(), datePic)) {

                from_txt.text = datePic

            } else {

                from_txt.text = to_txt.text.toString()

            }

        } else {

            to_txt_lay.isClickable = true

            datePic =
                StringBuilder().append(mYear).append("-").append(checkDigit(mMonth)).append("-")
                    .append(checkDigit(day)).append(" ").toString()

            if (checkDateAfter(datePic, from_txt.text.toString())) {

                to_txt.text = datePic

            } else {

                to_txt.text = datePic

                from_txt.text = datePic

            }

        }

    }

    override fun failure(inputText: String?) {

        editNameDialog?.dismiss()

        from_txt_lay.isClickable = true

        to_txt_lay.isClickable = true

    }

    private fun checkDigit(number: Int): String {

        return if (number <= 9) "0$number" else number.toString()

    }

    private fun checkDateBefore(date_End: String, date_Start: String): Boolean {

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

        val dateEnd = sdf.parse(date_End)

        val dateStart = sdf.parse(date_Start)

        return dateStart!!.before(dateEnd)

    }

    private fun checkDateAfter(date_End: String, date_Start: String): Boolean {

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

        val dateEnd = sdf.parse(date_End)

        val dateStart = sdf.parse(date_Start)

        return dateEnd!!.after(dateStart)

    }

    private fun getDate(timeStamp: Long): String {

        val date = Date(timeStamp * 1000)

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

        return sdf.format(date.time)

    }

}