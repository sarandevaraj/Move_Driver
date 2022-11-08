package com.taximobility.tripCancel.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.taximobility.MainHomeFragmentActivity
import com.taximobility.R
import com.taximobility.bookingmodule.BookTaxiHomePage
import com.taximobility.databinding.FragmentCancellationPaymentOptionsBinding
import com.taximobility.features.CToast
import com.taximobility.fragments.ReasonListFrag
import com.taximobility.fragments.TripHistory
import com.taximobility.service.GetPassengerUpdate
import com.taximobility.service.RetrofitCallbackClass
import com.taximobility.tripCancel.CancelTripRequestData
import com.taximobility.tripCancel.CancelTripResponseData
import com.taximobility.tripCancel.CreditCardData
import com.taximobility.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.taximobility.interfaces.APIResult
import com.taximobility.interfaces.CancelPayment
import com.taximobility.service.APIService_Retrofit_JSON
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_TRIP_ID = "trip_id"
private const val ARG_CANCEL_FARE = "cancellationFee"
private const val ARG_CANCEL_REASON = "cancelReason"
private const val ARG_FROM_PAGE = "fromPage"
private const val ARG_CREDIT_CARD = "creditCardData"
private const val ARG_PAYMENT_MODE_ID = "paymentModeId"
private const val PAYMENT_MODE_ID_WALLET = "5"
private const val PAYMENT_MODE_ID_NEXT_TRIP = "10"

class CancellationPaymentOptionsFragment : DialogFragment() {
    private var tripId = 0
    private var paymentModeId = ""
    private var cancellationFee = ""
    private var cancelReason = ""
    private var alertMessage = ""
    private var fromPage = 1
    private var creditCardData: CreditCardData? = null
    private var mDialog: Dialog? = null
    private var cancelPayListener: CancelPayment? = null

    private lateinit var binding: FragmentCancellationPaymentOptionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            tripId = getInt(ARG_TRIP_ID, 0)
            cancellationFee = getString(ARG_CANCEL_FARE) ?: ""
            cancelReason = getString(ARG_CANCEL_REASON) ?: ""
            fromPage = getInt(ARG_FROM_PAGE, 1)
            paymentModeId = getString(ARG_PAYMENT_MODE_ID) ?: ""
            if (containsKey(ARG_CREDIT_CARD))
                creditCardData = getParcelable(ARG_CREDIT_CARD)

        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        cancelPayListener = if (context is CancelPayment) context else null
    }

    override fun onDetach() {
        super.onDetach()
        cancelPayListener = null
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.run {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setWindowAnimations(R.style.dialogAnimation)
            isCancelable = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCancellationPaymentOptionsBinding.inflate(inflater, container, false)
        Colorchange.ChangeColor(binding.root as ViewGroup, requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            if (SessionSave.getWalletAmount(requireContext(), 0f) >= cancellationFee.toFloat()) {
                binding.tvWallet.visibility = View.VISIBLE
                binding.ivWallet.visibility = View.VISIBLE
            } else {
                binding.tvWallet.visibility = View.GONE
                binding.ivWallet.visibility = View.GONE
            }
        } catch (e: NumberFormatException) {
            binding.tvWallet.visibility = View.GONE
            binding.ivWallet.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.setOnKeyListener(object : android.content.DialogInterface.OnKeyListener {
            override fun onKey(dialog: android.content.DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) {
                    moveToPreviousPage()
                }
                return true
            }
        })

        binding.tvCard.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragment = CardListFragment.newInstance(tripId, cancellationFee, cancelReason, fromPage)
            fragment.show(fragmentManager, "cardListFragment")
            dismiss()

//            try {
//                val j = JSONObject()
//                j.put("passenger_id", SessionSave.getSession("Id", activity))
//                j.put("amount", cancellationFee)
//                val url = "type=get_order_id_razorpay"
//                CancelRazorpay(url, j)
//            } catch (e: Exception) {
//                // TODO: handle exception
//                e.printStackTrace()
//            }
        }
        binding.tvWallet.setOnClickListener {
            callCancelTripApi(PAYMENT_MODE_ID_WALLET)
        }
        binding.tvNextTrip.setOnClickListener {
            callCancelTripApi(PAYMENT_MODE_ID_NEXT_TRIP)
        }
    }

    override fun onStop() {
        println("onStop CancellationPaymentOptionsFragment")
        cancelLoading()
        super.onStop()
    }


    private fun moveToPreviousPage() {
        dismiss()
        val reasonListFrag = ReasonListFrag().apply {
            arguments = Bundle().apply {
                putString("From", "2")
                putString("trip_id", tripId.toString())
                putString("Cancel_fee", cancellationFee)
            }
        }
        reasonListFrag.show(requireActivity().supportFragmentManager, "reasonListFrag")
    }

    /**
     * Function which calls cancel_trip API to cancel that trip
     * @param paymentModeId - Payment mode ID. i.e, payment by CASH(2) or WALLET(5) or PAYMENT AT NEXT TRIP(10)
     * @param cardId - Passenger's card ID - default value 0
     * @param creditCardCvv - Passenger's card CVV number - default value empty
     */
    private fun callCancelTripApi(paymentModeId: String, cardId: Int = 0, creditCardCvv: String = "") {
        if (NetworkStatus.isOnline(requireContext())) {
            showLoading()
            val call = AppController.getInstance().apiManagerWithEncryptBaseUrl.callCancelTripApi(CancelTripRequestData(tripId, "4", cancelReason, paymentModeId, creditCardCvv, cardId))
            call.enqueue(RetrofitCallbackClass(requireContext(), object : Callback<CancelTripResponseData> {
                override fun onResponse(call: Call<CancelTripResponseData>, response: Response<CancelTripResponseData>) {
                    cancelLoading()
                    if (response.isSuccessful && response.body() != null) {
                        handleResponse(response.body() as CancelTripResponseData)
                    } else
                        CToast.ShowToast(requireContext(), NC.getString(R.string.server_error))
                }

                override fun onFailure(call: Call<CancelTripResponseData>, t: Throwable) {
                    cancelLoading()
                    t.printStackTrace()
                    CToast.ShowToast(requireContext(), NC.getString(R.string.server_error))
                }
            }))
        } else CToast.ShowToast(requireContext(), NC.getString(R.string.check_internet_connection))
    }

    /**
     * Function to handle API response
     * @param cancelTripResponseData - Cancel trip API response data
     */
    private fun handleResponse(cancelTripResponseData: CancelTripResponseData) {
        if (cancelTripResponseData.status == 1) {
            dismiss()
            if (SessionSave.getSession("multi_tripID", requireActivity()) == "" || SessionSave.getSession("multi_tripID", requireActivity()) == tripId.toString())
                SessionSave.saveSession("trip_id", "", requireActivity())
            SessionSave.saveSession("TaxiStatus", "", requireActivity())
            alertMessage = cancelTripResponseData.message + "\n" + NC.getString(R.string.canceled_amount) + " " + SessionSave.getSession("Currency", requireActivity()) + cancelTripResponseData.cancellation_amount + "\n" + NC.getString(R.string.canceled_from) + " " + cancelTripResponseData.cancellation_from
            val intent = Intent(requireContext(), GetPassengerUpdate::class.java)
            requireContext().stopService(intent)
            if (fromPage == 2) {
                TaxiUtil.close = 1
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, TripHistory()).commit()
            } else {
                CToast.ShowToast(requireActivity(), alertMessage)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, BookTaxiHomePage()).commitAllowingStateLoss()
            }

            cancelTripResponseData.wallet_amount?.let { wallet_amount ->
                try {
                    SessionSave.saveWalletAmount(wallet_amount.toFloat(), requireContext())
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            }

        } else if (cancelTripResponseData.status == 2) {
            dismiss()
            CToast.ShowToast(requireActivity(), cancelTripResponseData.message)
            if (SessionSave.getSession("multi_tripID", requireActivity()) == "" || SessionSave.getSession("multi_tripID", requireActivity()) == tripId.toString())
                SessionSave.saveSession("trip_id", "", requireActivity())
            SessionSave.saveSession("TaxiStatus", "", requireActivity())
            SessionSave.saveSession("TaxiStatus", "", requireActivity())
            alertMessage = cancelTripResponseData.message
            val serviceIntent = Intent(requireContext(), GetPassengerUpdate::class.java)
            requireContext().stopService(serviceIntent)

            if (fromPage == 2) {
                TaxiUtil.close = 1
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, TripHistory()).commit()
            } else {
                val intent = Intent(requireActivity(), MainHomeFragmentActivity::class.java)
                intent.putExtra("alert_message", alertMessage)
                CToast.ShowToast(requireActivity(), alertMessage)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, BookTaxiHomePage()).commit()
            }
        } else if (cancelTripResponseData.status == -1) {
            dismiss()
            CToast.ShowToast(requireActivity(), cancelTripResponseData.message)
            SessionSave.saveSession("TaxiStatus", "", requireActivity())
            if (SessionSave.getSession("multi_tripID", requireActivity()) == "" || SessionSave.getSession("multi_tripID", requireActivity()) == tripId.toString())
                SessionSave.saveSession("trip_id", "", requireActivity())
            SessionSave.saveSession("TaxiStatus", "", requireActivity())
            alertMessage = cancelTripResponseData.message
            val serviceIntent = Intent(requireContext(), GetPassengerUpdate::class.java)
            requireContext().stopService(serviceIntent)

            if (fromPage == 2) {
                TaxiUtil.close = 1
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, TripHistory()).commit()
            } else {
                val intent = Intent(requireActivity(), MainHomeFragmentActivity::class.java)
                intent.putExtra("alert_message", alertMessage)
                CToast.ShowToast(requireActivity(), alertMessage)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, BookTaxiHomePage()).commit()
            }
        } else if (cancelTripResponseData.status == 3) {
            CToast.ShowToast(requireActivity(), cancelTripResponseData.message)
        } else {
            CToast.ShowToast(requireActivity(), cancelTripResponseData.message)
        }
    }

    /**
     *
     */
    fun showLoading() {
        val activity = requireContext() as Activity
        if (activity.currentFocus != null) {
            if (mDialog != null && mDialog!!.isShowing)
                mDialog!!.dismiss()
            val view = View.inflate(requireContext(), R.layout.progress_bar, null)
            mDialog = Dialog(requireContext(), R.style.dialogwinddow)
            mDialog?.run {
                setContentView(view)
                setCancelable(false)
                show()
                val iv = findViewById<View>(R.id.giff) as ImageView
                val imageViewTarget = DrawableImageViewTarget(iv)
                Glide.with(requireContext())
                        .load(R.raw.loading_anim)
                        .into(imageViewTarget)
            }
        }
    }

    fun cancelLoading() {
        mDialog?.run {
            if (isShowing)
                dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tripId: Int, cancellationFee: String, reason: String, fromPage: Int, paymentModeId: String = "", selectedCard: CreditCardData? = null) = CancellationPaymentOptionsFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_TRIP_ID, tripId)
                putString(ARG_CANCEL_FARE, cancellationFee)
                putString(ARG_CANCEL_REASON, reason)
                putString(ARG_PAYMENT_MODE_ID, paymentModeId)
                putInt(ARG_FROM_PAGE, fromPage)
                selectedCard?.let { putParcelable(ARG_CREDIT_CARD, it) }
            }
        }
    }
    private inner class CancelRazorpay(url: String, data: JSONObject) : APIResult {
        init {
            if (NetworkStatus.isOnline(activity)) {
                APIService_Retrofit_JSON(activity, this, data, false).execute(url)
            } else {
                Log.e("No Internet Available", "no internet")
            }
        }

        override fun getResult(isSuccess: Boolean, result: String?) {
            // TODO Auto-generated method stub
            if (isSuccess) {
                try {
                    val json = JSONObject(result)
                    if (json.getInt("status") == 1) {
                        cancelPayListener?.onCancelPay(tripId, cancelReason, cancellationFee, "3", json.getString("order_id"))
                        dismiss()
                    } else {

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else {
                if (result != null) {
                    CToast.ShowToast(requireContext(), NC.getString(R.string.server_error))
                }
            }
        }
    }

}