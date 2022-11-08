package com.taximobility.bookingmodule.favourite

import android.app.AlertDialog
import android.app.Dialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.taximobility.R
import com.taximobility.databinding.FavListBinding
import com.taximobility.features.CToast
import com.taximobility.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.taximobility.bookingmodule.Interface.AddFavouriteListener
import org.json.JSONObject


class FavouriteDialog : AppCompatDialogFragment(), View.OnClickListener,AddFavouriteListener {

    private lateinit var layFavType1: LinearLayout
    private lateinit var layFavType2: LinearLayout
    private lateinit var layFavType3: LinearLayout
    private lateinit var layFavType4: LinearLayout
    private lateinit var otherDetails: LinearLayout
    private lateinit var txtOkOthers: TextView
    private lateinit var edtOthers: EditText

    val fav = MutableLiveData<Int>()

    private lateinit var favViewModel: FavouriteViewModel
    private val requestData = JSONObject()
    private var placeType: String = ""
    private var favPlace: String = ""
    private var favLat: Double = 0.0
    private var favLng: Double = 0.0
    private var loadingDialog: Dialog? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DataBindingUtil.inflate<FavListBinding>(LayoutInflater.from(context), R.layout.fav_list, null, false)
        favViewModel = ViewModelProviders.of(this@FavouriteDialog).get(FavouriteViewModel::class.java)
        binding.apply {
            myFavViewModel = favViewModel
            lifecycleOwner = this@FavouriteDialog
            executePendingBindings()
        }
        if (arguments != null) {
            favPlace = arguments!!.getString("p_favourite_place").toString()
            favLat = arguments!!.getDouble("p_fav_latitude")
            favLng = arguments!!.getDouble("p_fav_longtitute")
        }
        val rView: View = binding.root
        layFavType1 = rView.findViewById<View>(R.id.lay_fav_res1) as LinearLayout
        layFavType2 = rView.findViewById<View>(R.id.lay_fav_res2) as LinearLayout
        layFavType3 = rView.findViewById<View>(R.id.lay_fav_res3) as LinearLayout
        layFavType4 = rView.findViewById<View>(R.id.lay_fav_res4) as LinearLayout
        otherDetails = rView.findViewById<View>(R.id.other_details) as LinearLayout
        edtOthers = rView.findViewById<View>(R.id.et_others) as EditText
        txtOkOthers = rView.findViewById<View>(R.id.ok_others) as TextView
        val alert = AlertDialog.Builder(activity)
        FontHelper.applyFont(activity, rView.findViewById<View>(R.id.favouriteroot))
        Colorchange.ChangeColor(rView as ViewGroup, activity)
        alert.setView(rView)
        isCancelable = true
        favViewModel.favData.observe(this, Observer {
            if (it != null) {
                closeDialog()
                fav.value = it.status
                dialog?.dismiss()
                if (it.status == 1) {
                    CToast.ShowToast(activity, it.message)
                } else {
                    CToast.ShowToast(activity, it.message)
                }
            }
        })
        layFavType1.setOnClickListener(this)
        layFavType2.setOnClickListener(this)
        layFavType3.setOnClickListener(this)
        layFavType4.setOnClickListener(this)
        txtOkOthers.setOnClickListener(this)
        return alert.create()
    }

    override fun loadFavouriteData() {

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.lay_fav_res1 -> {
                placeType = "Home"
                callAddFavApi(placeType)
            }
            R.id.lay_fav_res2 -> {
                placeType = "Office"
                callAddFavApi(placeType)
            }
            R.id.lay_fav_res3 -> {
                placeType = "Airport"
                callAddFavApi(placeType)
            }
            R.id.lay_fav_res4 -> {
                placeType = "Others"
                otherDetails.visibility = View.VISIBLE
            }
            R.id.ok_others -> {
                if (edtOthers.text.toString() != "") {
                    otherDetails.visibility = View.GONE
                    placeType = edtOthers.text.toString()
                    edtOthers.setText("")
                    val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(txtOkOthers.windowToken, 0)
                    callAddFavApi(placeType)
                } else {
                    Utility.alert_view_dialog(activity, "" + NC.getString(R.string.message), "" + NC.getString(R.string.enter_other_details),
                            "" + NC.getString(R.string.ok), "", true, { dialog, which -> dialog.dismiss() }, { dialog, which -> dialog.dismiss() }, "")
                }
            }
            else -> {
            }
        }
    }

    private fun callAddFavApi(favType: String) {
        requestData.apply {
            put("passenger_id", SessionSave.getSession(PASS_ID, activity))
            put("p_favourite_place", favPlace)
            put("p_fav_latitude", favLat)
            put("p_fav_longtitute", favLng)
            put("d_favourite_place", "")
            put("d_fav_latitude", "")
            put("d_fav_longtitute", "")
            put("fav_comments", "")
            put("notes", "")
            put("p_fav_locationtype", favType)
        }
        showDialog()
        favViewModel.callAddFavApiCall(requestData)
    }


    //methods for route animation

    private fun showDialog() {
        try {
            if (NetworkStatus.isOnline(activity)) {
                if (loadingDialog != null && loadingDialog!!.isShowing)
                    loadingDialog!!.dismiss()
                val view = View.inflate(activity, R.layout.progress_bar, null)
                loadingDialog = Dialog(activity!!, R.style.dialogwinddow)
                loadingDialog!!.setContentView(view)
                loadingDialog!!.setCancelable(false)
                if (this != null)
                    loadingDialog!!.show()

                val iv = loadingDialog!!.findViewById<ImageView>(R.id.giff)
                val imageViewTarget = DrawableImageViewTarget(iv)
                Glide.with(this)
                        .load(R.raw.loading_anim)
                        .into(imageViewTarget)

            } else {
                CToast.ShowToast(context, NC.getString(R.string.check_internet_connection))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //method to close dialog
    fun closeDialog() {

        try {
            if (loadingDialog != null)
                if (loadingDialog!!.isShowing)
                    loadingDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}