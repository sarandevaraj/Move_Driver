package com.taximobility.pdview

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.taximobility.R
import com.taximobility.locationSearch.PlacesData
import com.taximobility.util.AndroidUtils.Companion.dpToPx
import java.util.*


class PickupDropView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    lateinit var stopArray: ArrayList<PlacesData>


    var isCollapsed = false
    private val locationListView = LocationListView(context)
    private val customIconView = CustomIconView(context)
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

    fun forceInvalidate() {
        val customMarkerParams = RelativeLayout.LayoutParams(dpToPx(50, context), locationListView.height)
        customMarkerParams.addRule(RelativeLayout.ALIGN_PARENT_START)
        customIconView.layoutParams = customMarkerParams
        customIconView.visibility = View.VISIBLE
    }


    fun setData(stopArray: ArrayList<PlacesData>) {
        this.stopArray = stopArray
        locationListView.setData(stopArray)
        customIconView.setData(stopArray.size)
        customIconView.collapsed(isCollapsed)
        val collapseParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        collapseParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        collapseParams.addRule(RelativeLayout.CENTER_VERTICAL)
        collapseParams.marginEnd = 10
        upDownImageView.layoutParams = collapseParams
        upDownImageView.setImageResource(R.drawable.ic_expand_more_black_12dp)
        upDownImageView.setBackgroundResource(R.drawable.circle_background)
        upDownImageView.setPadding(7, 7, 7, 7)
        if (stopArray.size <= 2)
            upDownImageView.visibility = View.GONE
        else
            upDownImageView.visibility = View.VISIBLE
        val listViewParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        listViewParams.addRule(RelativeLayout.START_OF, upDownImageView.id)
        listViewParams.addRule(RelativeLayout.END_OF, customIconView.id)
        locationListView.layoutParams = listViewParams
        locationListView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray))
        println("location listview height ${locationListView.height}")


    }

}