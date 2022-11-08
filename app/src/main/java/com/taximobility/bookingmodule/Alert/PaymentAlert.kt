package com.taximobility.bookingmodule.Alert

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.taximobility.R
import com.taximobility.bookingmodule.BookTaxiHomeViewModel
import com.taximobility.features.CToast
import com.taximobility.util.*

object PaymentAlert {
    var payType: Int = 0

    fun cashCardPay(context: Activity, intPaymentType: Int, viewModel: BookTaxiHomeViewModel, cashCardTxt: TextView) {
        payType = intPaymentType
        if (!SessionSave.getSession(TaxiUtil.isSplitOn, context, true)) {
            val view = View.inflate(context, R.layout.paymentdialog, null)
            Colorchange.ChangeColor(view as ViewGroup, context)
            Dialog(context, R.style.NewDialog).also {
                it.setContentView(view)
                it.setCancelable(false)
                it.setCanceledOnTouchOutside(false)
                it.show()
                FontHelper.applyFont(context, view)

                Colorchange.ChangeColor(it.findViewById(R.id.inner_content), context)
                val rgrp = it.findViewById<RadioGroup>(R.id.paymentdialog_rgrp)
                val rbtnCash = it.findViewById<RadioButton>(R.id.paymentdialog_rbtn_cash)
                val rbtnCard = it.findViewById<RadioButton>(R.id.paymentdialog_rbtn_card)
                val btnSubmit = it.findViewById<Button>(R.id.paymentdialog_btn_submit)
                val btnCancel = it.findViewById<Button>(R.id.paymentdialog_btn_cancel)

                when (payType) {
                    1 -> rbtnCash.isChecked = true
                    2 -> rbtnCard.isChecked = true
                    else -> {
                        rbtnCash.isChecked = false
                        rbtnCard.isChecked = false
                    }
                }
                btnSubmit.setOnClickListener {btnSubmit ->
                    val selectedId = rgrp.checkedRadioButtonId
                    val radioButton = it.findViewById<RadioButton>(selectedId)

                    if (radioButton != null) {
                        if (radioButton.text.toString() == NC.getString(R.string.payment_cash)) {
                            payType = 1
                            viewModel.setPaymentType(NC.getString(R.string.payment_cash))
                            cashCardTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash, 0, 0, 0)
                        } else if (radioButton.text.toString() == NC.getString(R.string.payment_card)) {
                            payType = 2
                            viewModel.setPaymentType(NC.getString(R.string.payment_card))
                            cashCardTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash_b, 0, 0, 0)
                        }

                        it.dismiss()
                    } else {
                        CToast.ShowToast(context, NC.getString(R.string.select_payment))
                    }
                }

                btnCancel.setOnClickListener {btnCancel ->
                    payType = 0
                    viewModel.setPaymentType(NC.getString(R.string.cash_card))
                    it.dismiss()
                }
            }

        } else {
            ShowToast.center(context, NC.getString(R.string.mode_selection_split))
        }

    }
}