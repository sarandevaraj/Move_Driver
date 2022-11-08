package com.taximobility.tripCancel


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import com.taximobility.Login.CardRegisterAct.*
import com.taximobility.MainActivity
import com.taximobility.MainHomeFragmentActivity
import com.taximobility.R
import com.taximobility.databinding.FragmentAddCardBinding
import com.taximobility.features.CToast.ShowToast
import com.taximobility.interfaces.APIResult
import com.taximobility.service.APIService_Retrofit_JSON
import com.taximobility.tripCancel.fragments.CardListFragment
import com.taximobility.util.*
import kotlinx.android.synthetic.main.fragment_add_card.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

private const val ARG_TRIP_ID = "trip_id"
private const val ARG_CANCEL_FARE = "cancellationFee"
private const val ARG_CANCEL_REASON = "cancelReason"
private const val ARG_FROM_PAGE = "fromPage"

private const val TOTAL_SYMBOLS = 19 // size of pattern 0000-0000-0000-0000
private const val TOTAL_DIGITS = 16 // max numbers of digits in pattern: 0000 x 4
private const val DIVIDER_MODULO = 5 // means divider position is every 5th symbol beginning with 1
private const val DIVIDER_POSITION = DIVIDER_MODULO - 1 // means divider position is every 4th symbol beginning with 0
private const val DIVIDER = ' '

class AddCardFragment : Fragment() {
    private var tripId = 0
    private var cancellationFee = ""
    private var cancelReason = ""
    private var fromPage = 1
    private var dialog: Dialog? = null
    private var expmonth = ""
    private var expyear = ""
    private var monthtouched: Boolean = false
    private var yeartouched: Boolean = false
    private var isDefault = 0
    private var curmonth = 0
    private var curyear = 0
    private lateinit var monthadapter: FontHelper.MySpinnerAdapterWhite
    private lateinit var yearadapter: FontHelper.MySpinnerAdapterWhite
    private var creditCardRepository: CreditCardRepository? = null
    private lateinit var binding: FragmentAddCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            tripId = getInt(ARG_TRIP_ID, 0)
            cancellationFee = getString(ARG_CANCEL_FARE) ?: ""
            cancelReason = getString(ARG_CANCEL_REASON) ?: ""
            fromPage = getInt(ARG_FROM_PAGE, 1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        creditCardRepository = CreditCardRepository.getRepository(requireContext())
        binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val monthLst = resources.getStringArray(R.array.monthlistary)
        var yearLst = resources.getStringArray(R.array.yearary)
        val cal = Calendar.getInstance()
        curmonth = cal.get(Calendar.MONTH)
        curyear = cal.get(Calendar.YEAR)
        val yearLstArr = ArrayList<String>()
        val monthLstArr = ArrayList<String>()
        var selectionYearPosition = 0
        for (i in yearLst.indices) {
            if (yearLst[i].equals(curyear.toString(), ignoreCase = true)) {
                selectionYearPosition = i
            }
        }
        yearLst = Array<String>(20) { i -> "" }
        for (i in 0..19) {
            yearLst[i] = (curyear + i).toString()
        }
        for (i in monthLst.indices)
            monthLstArr.add(monthLst[i])
        for (i in yearLst.indices)
            yearLstArr.add(yearLst[i])

        monthadapter = FontHelper.MySpinnerAdapterWhite(requireActivity(), R.layout.monthitem_spinnerlay, monthLstArr)
        monthspn.adapter = monthadapter

        yearadapter = FontHelper.MySpinnerAdapterWhite(requireActivity(), R.layout.monthitem_spinnerlay, yearLstArr)
        yearspn.adapter = yearadapter
        monthspn.setSelection(curmonth)
        monthspn.isSelected = true
        yearspn.setSelection(selectionYearPosition)
        yearspn.isSelected = true
    }

    override fun onResume() {
        super.onResume()
        setToolbar()
        cardnumEdt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().startsWith("4")) {
                    if (SessionSave.getSession(LANG, requireActivity()) == "ar" || SessionSave.getSession(LANG, requireActivity()) == "fa")
                        cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.card_visa, 0, 0, 0)
                    else
                        cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.card_visa, 0)
                } else if (s.toString().length >= 2) {
                    val prefix = Integer.parseInt(s.toString().substring(0, 2))
                    if (prefix in 51..55) {
                        if (SessionSave.getSession(LANG, requireActivity()) == "ar" || SessionSave.getSession(LANG, requireActivity()) == "fa")
                            cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.card_master, 0, 0, 0)
                        else
                            cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.card_master, 0)
                    } else {
                        cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                } else {
                    cardnumEdt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length, buildCorrecntString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER))
                }
            }
        })
        monthspn.setOnTouchListener { v, event ->
            monthtouched = true
            expmonth = "" + (curmonth + 1)
            monthadapter.notifyDataSetChanged()
            false
        }
        monthspn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, arg2: Int, arg3: Long) {
                val txt = view as TextView
                txt.setTextColor(CL.getColor(requireContext(), R.color.textviewcolor_light))
                txt.gravity = Gravity.START
                if (SessionSave.getSession(LANG, requireActivity()) == "ar" || SessionSave.getSession(LANG, requireActivity()) == "fa")
                    txt.gravity = Gravity.END
                if (!monthtouched)
                    txt.text = "" + NC.getString(R.string.reg_month)
                else {
                    expmonth = parent.getItemAtPosition(arg2).toString()
                    if (yearspn.selectedItem == curyear.toString() && Integer.parseInt(expmonth) <= curmonth) {
                        expmonth = (curmonth + 1).toString()
                        monthspn.setSelection(curmonth)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        yearspn.setOnTouchListener { v, event ->
            yeartouched = true
            expyear = "" + curyear
            yearadapter.notifyDataSetChanged()
            false
        }
        yearspn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, arg2: Int, arg3: Long) {
                val txt = view as TextView
                txt.setTextColor(CL.getColor(requireContext(), R.color.textviewcolor_light))
                txt.gravity = Gravity.START
                if (SessionSave.getSession(LANG, requireActivity()) == "ar" || SessionSave.getSession(LANG, requireActivity()) == "fa")
                    txt.gravity = Gravity.END
                if (!yeartouched)
                    txt.text = "" + NC.getString(R.string.reg_year)
                else {
                    if (parent.getItemAtPosition(arg2).toString() == curyear.toString() && Integer.valueOf(expmonth) < curmonth + 1)
                        monthspn.setSelection(curmonth)
                    expyear = parent.getItemAtPosition(arg2).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        submitBtn.setOnClickListener {
            cardRegisterData()
        }
    }

    override fun onStop() {
        println("onStop AddCardFragment")
        TaxiUtil.close = 1
        (requireActivity() as MainHomeFragmentActivity).let {
            it.setTitle_m(NC.getString(R.string.Confirmation))
            it.tool_bar_lay.visibility = View.VISIBLE
            with(it.left_icon) {
                tag = "backarrow"
                setImageResource(R.drawable.back)
            }
        }
        super.onStop()
    }

    private fun setToolbar() {
        with((requireActivity() as MainHomeFragmentActivity)) {
            setTitle_m(NC.getString(R.string.add_card))
            tool_bar_lay.visibility = View.VISIBLE
            with(left_icon) {
                tag = "backarrow"
                setImageResource(R.drawable.back)
            }
        }
    }

    fun onBackPressed() {
        moveToPreviousPage()
    }

    private fun moveToPreviousPage() {
        requireActivity().supportFragmentManager.findFragmentByTag("addCardFragment")?.let { it1 -> requireActivity().supportFragmentManager.beginTransaction().remove(it1).commit() }
        val fragment = CardListFragment.newInstance(tripId, cancellationFee, cancelReason, fromPage)
        fragment.show(requireActivity().supportFragmentManager, "cardListFragment")
    }

    /**
     * this method is used to register the card details
     */
    private fun cardRegisterData() {
        val cardNumber = cardnumEdt.text.toString().trim().replace("\\s".toRegex(), "")
        val cvvNumber = cvvEdt.text.toString().trim()
        val cardName = cardnameEdt.text.toString().trim()
        if (Utility.validations(Utility.ValidateAction.isValidCard, requireActivity(), cardNumber))
            if (Utility.validations(Utility.ValidateAction.isNullMonth, requireActivity(), expmonth))
                if (Utility.validations(Utility.ValidateAction.isNullYear, requireActivity(), expyear))
                    if (Utility.validations(Utility.ValidateAction.isValidCvv, requireActivity(), cvvNumber))
                        if (!termsTxt.isChecked)
                            dialog = Utility.alert_view_dialog(requireActivity(), "Message", "" + NC.getString(R.string.agree_the_terms_and_condition), "" + NC.getString(R.string.ok), "", true, { dialog, which -> dialog.dismiss() }, { dialog, which -> dialog.dismiss() }, "")
                        else {
                            try {
                                val j = JSONObject()
                                j.put("email", SessionSave.getSession("Email", requireActivity()))
                                j.put("creditcard_no", cardNumber)
                                j.put("expdatemonth", expmonth)
                                j.put("expdateyear", expyear)
                                j.put("creditcard_cvv", cvvNumber)
                                j.put("savecard", "1")
                                j.put("default", isDefault)
                                j.put("card_type", "P")
                                j.put("card_holder_name", cardName)
                                j.put("passenger_id", SessionSave.getSession(PASS_ID, requireActivity()))
                                val url = "type=add_card_details"
                                val creditCardData = CreditCardData(cardName, "", "P", expmonth, expyear, cardNumber, "", "1", cardNumber, "")
                                CardRegister(url, j, creditCardData)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
    }

    /**
     * This class used to register with card details
     * @author developer
     */
    private inner class CardRegister(url: String, data: JSONObject, val creditCardData: CreditCardData) : APIResult {
        init {

            APIService_Retrofit_JSON(requireActivity(), this, data, false).execute(url)
        }

        override fun getResult(isSuccess: Boolean, result: String) {
            Utility.closeDialog(MainActivity.mshowDialog)
            if (isSuccess) {
                try {
                    val json = JSONObject(result)
                    if (json.getInt("status") == 1) {
                        creditCardRepository?.insertCreditCard(creditCardData.apply {
                            id = json.getString("passenger_cardid")
                            card = json.getString("masked_creditcard_no")
                        })
                        moveToPreviousPage()
                    } else
                        dialog = Utility.alert_view_dialog(requireActivity(), "Message", "" + json.getString("message"),
                                "" + NC.getString(R.string.ok), "", true, { dialog, which -> dialog.dismiss() }, { dialog, which -> dialog.dismiss() }, "")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } else {
                requireActivity().runOnUiThread(Runnable { ShowToast(requireActivity(), getString(R.string.server_con_error)) })
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tripId: Int, cancellationFee: String, reason: String, fromPage: Int) = AddCardFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_TRIP_ID, tripId)
                putString(ARG_CANCEL_FARE, cancellationFee)
                putString(ARG_CANCEL_REASON, reason)
                putInt(ARG_FROM_PAGE, fromPage)
            }
        }
    }
}
