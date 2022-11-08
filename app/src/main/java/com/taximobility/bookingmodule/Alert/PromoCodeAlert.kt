package com.taximobility.bookingmodule.Alert

import android.app.Activity
import android.app.Dialog
import android.graphics.Point
import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.taximobility.R
import com.taximobility.features.CToast
import com.taximobility.interfaces.PromoClick
import com.taximobility.util.Colorchange
import com.taximobility.util.FontHelper
import com.taximobility.util.NC

class PromoCodeAlert(var promoApplyListener: PromoClick) {
    var promoCode: String = ""
    fun promoCode(context: Activity) {
        val view = View.inflate(context, R.layout.forgot_popup, null)
        Colorchange.ChangeColor(view as ViewGroup, context)
        Dialog(context, R.style.NewDialog).also {
            it.setContentView(view)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            it.show()
            Colorchange.ChangeColor(it.findViewById(R.id.inner_content), context)
            FontHelper.applyFont(context, view)
            val maxLengthPromoCode = context.resources.getInteger(R.integer.promoMaxLength)
            val mail = it.findViewById<EditText>(R.id.forgotmail).apply {
                imeOptions = EditorInfo.IME_ACTION_DONE
                inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
                filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLengthPromoCode))
            }
            val editFilters = mail.filters
            val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
            newFilters[editFilters.size] = InputFilter.AllCaps()
            mail.apply {
                filters = newFilters
                hint = NC.getString(R.string.enter_promo_code)
                setText(promoCode)
            }
            val ok = it.findViewById<TextView>(R.id.okbtn)
            ok.text = NC.getString(R.string.apply)
            val cancel = it.findViewById<TextView>(R.id.cancelbtn)
            cancel.visibility = View.VISIBLE
            val pointSize = Point()
            context.windowManager.defaultDisplay.getSize(pointSize)
            cancel.setOnClickListener { cancel ->
                val view = context.currentFocus
                if (view != null) {
                    mail.onEditorAction(EditorInfo.IME_ACTION_DONE)
                }
                it.dismiss()
            }
            ok.setOnClickListener(object : View.OnClickListener {
                private var Phone: String = ""

                override fun onClick(arg0: View) {
                    try {
                        Phone = mail.text.toString()
                        val view = context.currentFocus
                        if (view != null) {
                            mail.onEditorAction(EditorInfo.IME_ACTION_DONE)
                        }
                        promoCode = Phone
                        if (Phone.trim { it <= ' ' } == "")
                            CToast.ShowToast(context, NC.getString(R.string.promo_code_empty))
                        else {
                            promoApplyListener.onPromoApply()
                            it.dismiss()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            })
        }
    }

    fun getPromo(): String {
        return promoCode
    }
    fun updatePromo(promo:String) {
       promoCode=promo
    }


    fun setPromo() {
        promoCode = ""
    }
}
