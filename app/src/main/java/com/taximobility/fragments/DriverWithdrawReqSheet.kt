package com.taximobility.fragments

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.taximobility.R
import kotlinx.android.synthetic.main.fragment_pay_mode_selection.*




class DriverWithdrawReqSheet(private val listener: Listener) : BottomSheetDialogFragment() {
    lateinit var leftIcon:LinearLayout
    lateinit var behavior: BottomSheetBehavior<*>
    var payType:Int=1
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return  View.inflate(requireContext(),R.layout.withdraw_req_sheet,null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        leftIconTxt.setOnClickListener{
            dismiss()
        }
        cashTxt.setOnClickListener {
            payType = 1
            cashTxt.setTextColor(resources.getColor(R.color.black))
            cashTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash, 0, R.drawable.tick, 0)

            cardTxt.setTextColor(resources.getColor(R.color.textviewcolor_light))
            cardTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.credit_card, 0, 0, 0)
        }
        cardTxt.setOnClickListener {
            payType = 2
            cardTxt.setTextColor(resources.getColor(R.color.black))
            cardTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.credit_card, 0, R.drawable.tick, 0)

            cashTxt.setTextColor(resources.getColor(R.color.textviewcolor_light))
            cashTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cash, 0, 0, 0)
        }

        submit_btn.setOnClickListener {
            if (promocodeTxt.text.toString()=="") {
                listener.invoke("", payType.toString())
                dismiss()
            }
            else{
                listener.invoke(promocodeTxt.text.toString(), payType.toString())
            }
        }
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        }
        return dialog
    }
    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
                bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet)
        behavior= BottomSheetBehavior.from<FrameLayout?>((bottomSheet as FrameLayout?)!!)
        val layoutParams = bottomSheet!!.layoutParams
        val windowHeight: Int = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.peekHeight= BottomSheetBehavior.PEEK_HEIGHT_AUTO
//        behavior.state= BottomSheetBehavior.STATE_COLLAPSED
//        extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels) / 4;
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay
                .getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }



}