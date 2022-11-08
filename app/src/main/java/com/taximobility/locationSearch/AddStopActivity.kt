package com.taximobility.locationSearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.taximobility.R
import com.taximobility.features.CToast
import com.taximobility.service.RetrofitCallbackClass
import com.taximobility.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import kotlinx.android.synthetic.main.activity_add_stop.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

const val ADD_STOP_REQUEST_CODE = 201
const val IS_FROM_ONGOING = "isFromOnGoing"
const val STOP_SLAB_SIZE = "slabsize"
var LOCAL_SLAB_SIZE = 0

class AddStopActivity : AppCompatActivity() {

    var stopDataArray = HashMap<String, PlacesData>()
    private var list = ArrayList<PlacesData>()
    private var isFromOnGoing = false
    private var hintText = ""
    private var onlyPackage: String? = null
    private var isStopUpdate = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_stop)
        intent?.run {
            list = getParcelableArrayListExtra(BUNDLE_PICKUP_DROP_ADDRESS)!!
            onlyPackage = getStringExtra("Package")
            isFromOnGoing = getBooleanExtra(IS_FROM_ONGOING, false)
            if (getIntExtra(STOP_SLAB_SIZE, -1) != -1) {
                LOCAL_SLAB_SIZE = getIntExtra(STOP_SLAB_SIZE, -1)
                hintText = NC.getString(R.string.enter_dest)
                desc.visibility = View.GONE
                header.visibility = View.GONE
                info_image.visibility = View.GONE
            } else {
                LOCAL_SLAB_SIZE = SLAB_SIZE
                hintText = NC.getString(R.string.search_stop_hint)

                desc.visibility = View.VISIBLE
                header.visibility = View.VISIBLE
                info_image.visibility = View.VISIBLE
            }
        }
        stopDataArray.forEach { }
        stopDataArray = list.associateBy({ it.id.toString() }, { it }) as HashMap<String, PlacesData>
        createDynamicLay()
    }

    override fun onStart() {
        super.onStart()
        layoutLoading.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        submit_button.setOnClickListener {
            println("stop array $stopDataArray")
            if (!isFromOnGoing) {
                setResult(Activity.RESULT_OK, Intent().apply {
                    putParcelableArrayListExtra(BUNDLE_PICKUP_DROP_ADDRESS, ArrayList(stopDataArray.values))
                    putExtra("Package", onlyPackage)
                })
                finish()
            } else {
                if (isStopUpdate)
                    callUpdatesStopsApi()
                else
                    finish()

            }
        }
        back_arrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun callUpdatesStopsApi() {
        showLoading()
//        val client = ServiceGenerator(this, false).createService(CoreClient::class.java)
        val client = AppController.getInstance().apiManagerWithEncryptBaseUrl
        val stopArray = JSONArray()
        stopDataArray.forEach {
            val stopData = it.value
            stopArray.put(JSONObject().apply {
                put("id", stopData.id)
                put("lat", stopData.lat)
                put("lng", stopData.lng)
                put("placeId", stopData.placeId)
                put("placeName", stopData.placeName)
                put("favPlaceType", stopData.favPlaceType)
            })
        }
        val requestData = JSONObject().apply {
            put("stops", stopArray)
            put("tripId", SessionSave.getSession("trip_id", this@AddStopActivity))
        }
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), requestData.toString())
        val coreRequest = client.updateStops(requestBody, SessionSave.getSession(LANG, this))
        coreRequest.enqueue(RetrofitCallbackClass(this, object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                hideLoading()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val jsonResponse = JSONObject(response.body()!!.string())
                        if (jsonResponse.get("status") == 1) {
                            setResult(Activity.RESULT_OK, Intent().apply {
                                putParcelableArrayListExtra(BUNDLE_PICKUP_DROP_ADDRESS, ArrayList(stopDataArray.values))
                            })
                            finish()
                        }
                    }
                } else
                    CToast.ShowToast(this@AddStopActivity, NC.getString(R.string.server_error))
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                hideLoading()
                CToast.ShowToast(this@AddStopActivity, NC.getString(R.string.server_error))
            }
        }))
    }

    private fun showLoading() {
        layoutLoading.visibility = View.VISIBLE
        val imageViewTarget = DrawableImageViewTarget(imgLoader)
        Glide.with(this)
                .load(R.raw.loading_anim)
                .into(imageViewTarget)
    }

    private fun hideLoading() {
        layoutLoading.visibility = View.GONE
    }

    /**
     * Function to clear existing view and create new views based on stopDataArray values
     * And to call validateDone() function to validate created views reached SLAB_SIZE
     */
    private fun createDynamicLay() {
        clearLay()
        stopDataArray.forEach {
            createStop(it.value)
        }

        if (stopDataArray.size <= LOCAL_SLAB_SIZE)
            createStop(PlacesData(Date().time.toInt(), 0.0, 0.0, "", "", "", ""))
        validateDone()
    }

    /**
     * Function to remove existing views and draw lines (ie., pickup, drop connecting)
     *<p>
     * stopDataArray - ArrayList of PlacesData values to create connecting lines dynamically
     * <p>
     */
    private fun clearLay() {
        lay_clear.removeAllViews()
        lay_location.removeAllViews()
        lay_marker.removeAllViews()
        lay_pickup.removeAllViews()
        lay_marker.addView(CustomIconView(this, minOf(stopDataArray.size + 1, LOCAL_SLAB_SIZE + 1), true))
    }

    /**
     * Method to create pick up, drop and stop(if available) views dynamically
     *
     * @param stopData     - Model class data for view (ie., pickup or drop or stop)
     */
    private fun createStop(stopData: PlacesData) {
        lay_location.addView(AppCompatTextView(this).apply {
            isFocusableInTouchMode = false
            hint = hintText
            id = stopData.id
            setTextColor(ContextCompat.getColor(this@AddStopActivity, R.color.textNormalColor))
            val layParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, resources.getDimension(R.dimen.stop_lay_height).toInt())
            layParams.setMargins(0, 0, 0, resources.getDimension(R.dimen.stop_space_lay).toInt())
            setPadding(resources.getDimension(R.dimen.stop_space_lay).toInt(), 0, 0, 0)
            layoutParams = layParams
            gravity = Gravity.CENTER_VERTICAL
            ellipsize = TextUtils.TruncateAt.MARQUEE
            isSelected = true
            isSingleLine = true
            setOnClickListener {
                if (isFromOnGoing && lay_location.getChildAt(0) == it) {
                    CToast.ShowToast(this@AddStopActivity, NC.getString(R.string.pickUpCannotChanged))
                } else {
                    startActivityForResult(Intent(this@AddStopActivity, LocationSearchActivity::class.java).apply {
                        putExtras(Bundle().apply {
                            putString(BUNDLE_STOP_ID, "" + it.id)
                        })
                    }, 1)
                }
            }
            text = stopData.placeName
            setBackgroundColor(ContextCompat.getColor(this@AddStopActivity, R.color.stopTextBackgroundColor))
        })

        createClearStopLay(stopData)

    }

    /**
     * Function to add remove icon to clear added stop
     *
     * @param stopData - PlacesData object to check remove icon can be add or not based on ID
     */
    private fun createClearStopLay(stopData: PlacesData) {
        lay_clear.addView(AppCompatImageView(this).apply {
            val layParams = LinearLayout.LayoutParams(resources.getDimension(R.dimen.stop_lay_height).toInt(), resources.getDimension(R.dimen.stop_lay_height).toInt())
            setPadding(15, 15, 15, 15)
            layParams.setMargins(0, 0, 0, resources.getDimension(R.dimen.stop_space_lay).toInt())
            layoutParams = layParams
            tag = "" + stopData.id
            if (stopData.id != 0 && !stopData.placeName.isEmpty()) {
                setImageResource(R.drawable.ic_clear_black_24dp)
            } else {
                isClickable = false
                isEnabled = false
            }
            setOnClickListener {
                stopDataArray.remove(it.tag)
                isStopUpdate = true
                createDynamicLay()
            }
        })
    }

    /**
     * Function to enable done button based on stopDataArray size - Min two required (ie., pickup and drop)
     */
    private fun validateDone() {
        if (isStopUpdate) {
            submit_button.isClickable = true
            submit_button.isEnabled = true
            submit_button.setBackgroundColor(ContextCompat.getColor(this@AddStopActivity, R.color.button_accept))
        } else {
            submit_button.setBackgroundColor(ContextCompat.getColor(this@AddStopActivity, R.color.button_unselect))
            submit_button.isClickable = false
            submit_button.isEnabled = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                isStopUpdate = true
                data?.let {
                    it.extras?.run {
                        val address = getString(BUNDLE_STOP_ADDRESS)
                        val id = getString(BUNDLE_STOP_ID)?.toInt()
                        val lat = getDouble(BUNDLE_STOP_LAT, 0.0)
                        val lng = getDouble(BUNDLE_STOP_LNG, 0.0)
                        val placeId = data.getStringExtra(BUNDLE_STOP_PID)
                        val favPlaceType = data.getStringExtra(BUNDLE_BOOKING_ADDRESS_TYPE)
                        if (!id!!.equals("") && address != "") {
                            stopDataArray[id.toString()] = PlacesData(id, lat, lng, address.toString(), placeId.toString(), "", favPlaceType)
                            createDynamicLay()
                        }
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}