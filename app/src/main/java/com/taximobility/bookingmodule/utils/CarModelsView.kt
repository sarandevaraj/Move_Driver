package com.taximobility.bookingmodule.utils

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.taximobility.R
import com.taximobility.bookingmodule.BookTaxiHomeViewModel
import com.taximobility.bookingmodule.Data.ModelData
import com.taximobility.util.SessionSave
import com.squareup.picasso.Picasso
import com.taximobility.util.FontHelper
import com.taximobility.util.TaxiUtil
import org.json.JSONArray
import java.util.*

class CarModelsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        HorizontalScrollView(context, attrs, defStyleAttr) {



    private lateinit var modelArray: ArrayList<ModelData>

    private var selectedPosition = 0
    private var selectedModelId: String = ""
    private val mLinearLayout: LinearLayout = LinearLayout(context).apply {
        layoutParams =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
    }
    private val displayMetrics = DisplayMetrics()
    private lateinit var viewModel: BookTaxiHomeViewModel

    private var currentDateTimeString: Long = 0
    private var previousClickedTime: Long = 0
    private var previousSelectedModel: Int = 0
    private var selectedCarModel: Int = 0
    private var selectedModelSize: String = ""

    init {
        addView(mLinearLayout)
        (context as AppCompatActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        selectedPosition = try {
            SessionSave.getSession("selected_carmodel", context).toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    fun setCarModelArray(array: String, viewModel: BookTaxiHomeViewModel) {
        modelArray = ArrayList()
        this.viewModel = viewModel
        val modelJson = JSONArray(array)
        for (n in 0 until modelJson.length()) {
            var modelSize = ""
            if (modelJson.getJSONObject(n).has("model_size")) {
                modelSize = modelJson.getJSONObject(n).getString("model_size")
            }
            var modelId = ""
            if(modelJson.getJSONObject(n).has("model_id")){
                modelId = modelJson.getJSONObject(n).getString("model_id")
            }
            if(SessionSave.getSession(TaxiUtil.CORPORATE_PASSENGER,context) == "1") {
                if(modelId == "-1"){

                }else {
                    modelArray.add(
                            ModelData(
                                    modelJson.getJSONObject(n).getString("model_id"),
                                    modelJson.getJSONObject(n).getString("model_name"),
                                    modelJson.getJSONObject(n).getString("unfocus_image"),
                                    modelJson.getJSONObject(n).getString("focus_image"),
                                    modelSize
                            )
                    )
                }
            }else{
                modelArray.add(
                        ModelData(
                                modelJson.getJSONObject(n).getString("model_id"),
                                modelJson.getJSONObject(n).getString("model_name"),
                                modelJson.getJSONObject(n).getString("unfocus_image"),
                                modelJson.getJSONObject(n).getString("focus_image"),
                                modelSize
                        )
                )
            }
        }
        if (modelArray.size >= selectedPosition)
            selectedModelId = modelArray[selectedPosition].model_id
        else if (modelArray.size > 0) {
            selectedModelId = modelArray[0].model_id
            selectedPosition = 0
        }
        createViews(modelArray)
    }

    private fun createViews(modelArray: ArrayList<ModelData>) {
        mLinearLayout.removeAllViews()
        for (i in 0 until modelArray.size) {
            val view = LayoutInflater.from(context).inflate(R.layout.bottom_lay_car_new, mLinearLayout, false).apply {
                FontHelper.applyFont(context, findViewById(R.id.lay_model_one))

                if (modelArray.size > 4) {
                    this@CarModelsView.post {
//                        layoutParams = LinearLayout.LayoutParams(
//                                (this@CarModelsView.width / 4.5).toInt(),
//                                ViewGroup.LayoutParams.WRAP_CONTENT
                        //                       )
                    }
                } else {
                    this@CarModelsView.post {
//                        layoutParams = LinearLayout.LayoutParams(
//                                (this@CarModelsView.width / modelArray.size),
//                                ViewGroup.LayoutParams.WRAP_CONTENT
//                        )
                    }
                }
            }
            view.findViewById<ImageView>(R.id.txt_dra_car1).also {
                Picasso.get().load(modelArray[i].unfocus_image).error(R.drawable.car2_unfocus).into(it)
            }
            view.findViewById<TextView>(R.id.txt_model1).also {
                it.text = modelArray[i].model_name
            }
            val modelLayout = view.findViewById<LinearLayout>(R.id.lay_model_one)
            modelLayout.tag = i
            modelLayout.setOnClickListener {
                selectedPosition = if (modelArray[i].model_id != "-1") i else 0
                SessionSave.saveSession("selected_carmodel", selectedPosition.toString(), context)
                selectedCarModel = modelLayout.tag as Int
                if (previousSelectedModel == selectedCarModel) {
                    currentDateTimeString = System.currentTimeMillis()
                    if (currentDateTimeString - previousClickedTime <= 2000) {

                    } else {
                        carLayClick(selectedCarModel)
                    }
                } else {
                    carLayClick(selectedCarModel)
                }
                previousSelectedModel = selectedCarModel
                previousClickedTime = System.currentTimeMillis()
            }
            mLinearLayout.addView(view)
        }
        carLayClick(selectedPosition)
    }

    private fun carLayClick(position: Int) {
        selectedModelId = modelArray[position].model_id
        selectedModelSize = modelArray[position].model_size
        for (i in 0 until mLinearLayout.childCount) {
            val carLayout = mLinearLayout.getChildAt(i)
            val carImage = carLayout.findViewById<ImageView>(R.id.txt_dra_car1)
            val carModelName = carLayout.findViewById<TextView>(R.id.txt_model1)
            val modelSize = carLayout.findViewById<TextView>(R.id.modelSize)
            val carModelFare = carLayout.findViewById<TextView>(R.id.pickup_approx_fare)
            val carModelTime = carLayout.findViewById<TextView>(R.id.txt_model_time)
            carModelName.text = modelArray[i].model_name
            modelSize.text = modelArray[i].model_size
            carModelFare.visibility = View.INVISIBLE
            carModelTime.visibility = View.INVISIBLE
            if (carLayout.tag == position) {
                Picasso.get().load(modelArray[i].focus_image).error(R.drawable.car2_unfocus).into(carImage)
                carModelName.setTextColor(ContextCompat.getColor(context, R.color.button_accept))
                modelSize.setTextColor(ContextCompat.getColor(context, R.color.button_accept))
                modelSize.visibility = View.VISIBLE
            } else {
                Picasso.get().load(modelArray[i].unfocus_image).error(R.drawable.car2_unfocus).into(carImage)
                carModelName.setTextColor(ContextCompat.getColor(context, R.color.textviewcolor_light))
                modelSize.visibility = View.INVISIBLE
            }
            if (i== mLinearLayout.childCount-1){
                modelSize.visibility = View.INVISIBLE
            }


        }
        viewModel.carLayClick.value = true

//        println("MODEl SIZE ${modelArray[position].model_size}")
    }

    fun getSelectedCarModelSize():String {
        return selectedModelSize
    }
    fun getSelectedCarModel(): String {
        return selectedModelId
    }

    internal fun setApproximateFare(approximateFare: Double) {
        for (i in 0 until mLinearLayout.childCount) {
            val carLayout = mLinearLayout.getChildAt(i)
            val carModelFare = carLayout.findViewById<TextView>(R.id.pickup_approx_fare)
            if (approximateFare != 0.0) {
                carModelFare.text = SessionSave.getSession("Currency", context) + NumberFormat.convertDouble(String.format(Locale.UK, approximateFare.toString()).toDouble())
                carModelFare.setTextColor(ContextCompat.getColor(context, R.color.button_accept))
                if (carLayout.tag == selectedPosition)
                    carModelFare.visibility = View.VISIBLE
                else
                    carModelFare.visibility = View.INVISIBLE
            } else {
                carModelFare.visibility = View.INVISIBLE
            }
        }
    }

    internal fun setApproximateTime(approximateTime: Double) {
        for (i in 0 until mLinearLayout.childCount) {
            val carLayout = mLinearLayout.getChildAt(i)
            val carModelTime = carLayout.findViewById<TextView>(R.id.txt_model_time)
            if (approximateTime != 0.0) {
                carModelTime.text =String.format(Locale.UK, approximateTime.toInt().toString())+"mins"
                carModelTime.setTextColor(ContextCompat.getColor(context, R.color.button_accept))
                if (carLayout.tag == selectedPosition && i!= mLinearLayout.childCount-1)
                    carModelTime.visibility = View.VISIBLE
                else
                    carModelTime.visibility = View.INVISIBLE
            } else {
                carModelTime.visibility = View.INVISIBLE
            }
        }
    }


    private fun dpToPx(dp: Int, con: Context): Int {
        val displayMetrics = con.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

}