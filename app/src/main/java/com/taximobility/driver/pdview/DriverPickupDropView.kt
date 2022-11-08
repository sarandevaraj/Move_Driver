package com.taximobility.driver.pdview

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.AppCompatImageView
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.taximobility.R
import com.taximobility.driver.route.DriverStopData
import com.taximobility.driver.utils.DriverCircleOverlayView.dpToPx

@SuppressLint("ResourceType")
class DriverPickupDropView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private lateinit var driverStopArray: ArrayList<DriverStopData>
    var lang: String = ""


    private var isCollapsed = false
    private val locationListView = DriverLocationListView(context)
    private val customIconView = DriverCustomIconView(context)
    private val upDownImageView = AppCompatImageView(context)

    init {
        customIconView.id = 1
        locationListView.id = 2
        upDownImageView.id = 3
        addView(customIconView)
        addView(locationListView)
        addView(upDownImageView)
        customIconView.visibility = View.GONE
        upDownImageView.setOnClickListener {
            isCollapsed = !isCollapsed
            customIconView.collapsed(isCollapsed)
            locationListView.collapsed(isCollapsed)
            if (!isCollapsed)
                it.rotation = 180.0f
            else
                it.rotation = 0.0f
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.VISIBLE) {
            locationListView.isFocusable = true
            locationListView.isClickable = true
            for (i in 0 until locationListView.childCount) {
                val textView = locationListView.getChildAt(i) as TextView
                textView.isFocusable = true
                textView.isClickable = true
                textView.post {
                    textView.isSingleLine = true
                    textView.ellipsize = TextUtils.TruncateAt.MARQUEE
                    textView.marqueeRepeatLimit = -1
                    textView.isSelected = true
                }

            }
        }
    }

    fun forceInvalidate() {
        val customMarkerParams = LayoutParams(dpToPx(30), locationListView.height)

        if (lang == "ar" || lang == "fa") {
            customMarkerParams.addRule(ALIGN_PARENT_RIGHT)
        } else {
            customMarkerParams.addRule(ALIGN_PARENT_START)
        }
        customIconView.layoutParams = customMarkerParams
        customIconView.visibility = View.VISIBLE
    }


    fun setData(driverStopArray: ArrayList<DriverStopData>, type: String, language: String) {
        lang = language

        if (lang == "ar" || lang == "fa") {

            this.driverStopArray = driverStopArray
            locationListView.setData(driverStopArray, type, language)
            customIconView.setData(driverStopArray.size)
            customIconView.collapsed(isCollapsed)
            val collapseParams = LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            collapseParams.addRule(ALIGN_PARENT_LEFT)
            collapseParams.addRule(CENTER_VERTICAL)
            collapseParams.marginEnd = 10
            upDownImageView.layoutParams = collapseParams
            upDownImageView.setImageResource(R.drawable.driver_ic_expand_more_black_24dp)
            upDownImageView.setBackgroundResource(R.drawable.driver_circle_background)
            upDownImageView.setPadding(7, 7, 7, 7)
            if (driverStopArray.size <= 2)
                upDownImageView.visibility = View.INVISIBLE
            else
                upDownImageView.visibility = View.VISIBLE
            val listViewParams = LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            listViewParams.addRule(RIGHT_OF, upDownImageView.id)
            listViewParams.addRule(LEFT_OF, customIconView.id)
            locationListView.layoutParams = listViewParams
        } else {
            this.driverStopArray = driverStopArray
            locationListView.setData(driverStopArray, type, language)
            customIconView.setData(driverStopArray.size)
            val collapseParams = LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            collapseParams.addRule(ALIGN_PARENT_END)
            collapseParams.addRule(CENTER_VERTICAL)
            collapseParams.marginEnd = 10
            upDownImageView.layoutParams = collapseParams
            upDownImageView.setImageResource(R.drawable.driver_ic_expand_more_black_24dp)
            upDownImageView.setBackgroundResource(R.drawable.driver_circle_background)
            upDownImageView.setPadding(7, 7, 7, 7)
            if (driverStopArray.size <= 2)
                upDownImageView.visibility = View.GONE
            else
                upDownImageView.visibility = View.VISIBLE
            val listViewParams = LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            listViewParams.addRule(START_OF, upDownImageView.id)
            listViewParams.addRule(END_OF, customIconView.id)
            locationListView.layoutParams = listViewParams
        }

        if (type.equals("ONGOING", ignoreCase = true)) {
            locationListView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray))
        }

    }


}