package com.taximobility.tripCancel.fragments

import android.app.Activity
import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.taximobility.R
import com.taximobility.adapter.CreditCardAdapter
import com.taximobility.bookingmodule.BookTaxiHomePage
import com.taximobility.databinding.FragmentCardListBinding
import com.taximobility.features.CToast
import com.taximobility.fragments.TripHistory
import com.taximobility.service.GetPassengerUpdate
import com.taximobility.service.RetrofitCallbackClass
import com.taximobility.tripCancel.*
import com.taximobility.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val PAYMENT_MODE_ID_CARD = "2"
private const val ARG_TRIP_ID = "trip_id"
private const val ARG_CANCEL_FARE = "cancellationFee"
private const val ARG_CANCEL_REASON = "cancelReason"
private const val ARG_FROM_PAGE = "fromPage"

class CardListFragment : DialogFragment(), CreditCardAdapter.RecyclerViewItemClickListener {
    private var tripId = 0
    private var cancellationFee = ""
    private var cancelReason = ""
    private var alertMessage = ""
    private var fromPage = 1
    private var mDialog: Dialog? = null
    private lateinit var viewModel: CreditCardViewModel
    private lateinit var binding: FragmentCardListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            tripId = getInt(ARG_TRIP_ID, 0)
            cancellationFee = getString(ARG_CANCEL_FARE) ?: ""
            cancelReason = getString(ARG_CANCEL_REASON) ?: ""
            fromPage = getInt(ARG_FROM_PAGE, 1)
        }
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
        viewModel = ViewModelProviders.of(this, CommonViewModelFactory(requireContext())).get(CreditCardViewModel::class.java)
        binding = FragmentCardListBinding.inflate(inflater, container, false)
        Colorchange.ChangeColor(binding.root as ViewGroup, requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cardAdapter = CreditCardAdapter(requireContext(), this)
        binding.rvCardList.adapter = cardAdapter
        viewModel.getCardList()?.observe(this, androidx.lifecycle.Observer {
            if (it != null && it.isNotEmpty()) {
                val cardList = ArrayList<CreditCardData>()
                cardList.addAll(it)
                // cardList.add(CreditCardData("", "", "", "", "", "" + NC.getString(R.string.addcard), "", "", "", ""))
                cardAdapter.submitList(cardList)
            } /*else
                cardAdapter.submitList(arrayListOf(CreditCardData("", "", "", "", "", "" + NC.getString(R.string.addcard), "", "", "", "")))*/
        })
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
    }

    override fun onStop() {
        println("onStop CardListFragment")
        cancelLoading()
        super.onStop()
    }


    private fun moveToPreviousPage(selectedCard: CreditCardData? = null) {
        dismiss()
        val fragment = CancellationPaymentOptionsFragment.newInstance(tripId, cancellationFee, cancelReason, fromPage, PAYMENT_MODE_ID_CARD, selectedCard)
        fragment.show(requireActivity().supportFragmentManager, "cancellationPaymentOptionsFragment")
    }

    override fun onClick(cardData: CreditCardData, position: Int) {
        if (cardData.card == NC.getString(R.string.addcard))
            moveToAddCardPage()
        else
            callCancelTripApi(PAYMENT_MODE_ID_CARD, cardId = cardData.id.toInt())
    }

    private fun moveToAddCardPage() {
        dismiss()
        requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.in_anim, R.anim.out_anim).add(R.id.mainFrag, AddCardFragment.newInstance(tripId, cancellationFee, cancelReason, fromPage), "addCardFragment").addToBackStack(null).commit()

    }

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
                    t.printStackTrace()
                    cancelLoading()
                    CToast.ShowToast(requireContext(), NC.getString(R.string.server_error))
                }
            }))
        } else CToast.ShowToast(requireContext(), NC.getString(R.string.check_internet_connection))
    }

    private fun handleResponse(cancelTripResponseData: CancelTripResponseData) {
        if (cancelTripResponseData.status == 1) {
            if (SessionSave.getSession("multi_tripID", requireActivity()) == "" || SessionSave.getSession("multi_tripID", requireActivity()) == tripId.toString())
                SessionSave.saveSession("trip_id", "", requireActivity())
            SessionSave.saveSession("TaxiStatus", "", requireActivity())
            alertMessage = cancelTripResponseData.message + "\n" + NC.getString(R.string.canceled_amount) + " " + SessionSave.getSession("Currency", requireActivity()) + cancelTripResponseData.cancellation_amount + "\n" + NC.getString(R.string.canceled_from) + " " + cancelTripResponseData.cancellation_from
            val intent = Intent(requireContext(), GetPassengerUpdate::class.java)
            requireContext().stopService(intent)
            if (fromPage == 2) {
                println("onStop handleResponse 1")
                TaxiUtil.close = 1
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, TripHistory()).commit()
            } else {
                println("onStop handleResponse 2")
                CToast.ShowToast(requireActivity(), alertMessage)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, BookTaxiHomePage()).commit()
            }

        } else if (cancelTripResponseData.status == 2) {
            CToast.ShowToast(requireActivity(), cancelTripResponseData.message)
            if (SessionSave.getSession("multi_tripID", requireActivity()) == "" || SessionSave.getSession("multi_tripID", requireActivity()) == tripId.toString())
                SessionSave.saveSession("trip_id", "", requireActivity())
            SessionSave.saveSession("TaxiStatus", "", requireActivity())
            SessionSave.saveSession("TaxiStatus", "", requireActivity())
            alertMessage = cancelTripResponseData.message
            val serviceIntent = Intent(requireContext(), GetPassengerUpdate::class.java)
            requireContext().stopService(serviceIntent)

            if (fromPage == 2) {
                println("onStop handleResponse 3")
                TaxiUtil.close = 1
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, TripHistory()).commit()
            } else {
                println("onStop handleResponse 4")
                CToast.ShowToast(requireActivity(), alertMessage)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, BookTaxiHomePage()).commit()
            }
        } else if (cancelTripResponseData.status == -1) {
            CToast.ShowToast(requireActivity(), cancelTripResponseData.message)
            SessionSave.saveSession("TaxiStatus", "", requireActivity())
            if (SessionSave.getSession("multi_tripID", requireActivity()) == "" || SessionSave.getSession("multi_tripID", requireActivity()) == tripId.toString())
                SessionSave.saveSession("trip_id", "", requireActivity())
            SessionSave.saveSession("TaxiStatus", "", requireActivity())
            alertMessage = cancelTripResponseData.message
            val serviceIntent = Intent(requireContext(), GetPassengerUpdate::class.java)
            requireContext().stopService(serviceIntent)

            if (fromPage == 2) {
                println("onStop handleResponse 5")
                TaxiUtil.close = 1
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, TripHistory()).commit()
            } else {
                println("onStop handleResponse 6")
                CToast.ShowToast(requireActivity(), alertMessage)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFrag, BookTaxiHomePage()).commit()
            }
        } else if (cancelTripResponseData.status == 3) {
            CToast.ShowToast(requireActivity(), cancelTripResponseData.message)
        } else {
            CToast.ShowToast(requireActivity(), cancelTripResponseData.message)
        }

        dialog?.dismiss()
    }

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
        fun newInstance(tripId: Int, cancellationFee: String, reason: String, fromPage: Int) = CardListFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_TRIP_ID, tripId)
                putString(ARG_CANCEL_FARE, cancellationFee)
                putString(ARG_CANCEL_REASON, reason)
                putInt(ARG_FROM_PAGE, fromPage)
            }
        }
    }
}