package com.taximobility.pdview

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.appcompat.widget.AppCompatTextView
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import com.taximobility.R
import com.taximobility.locationSearch.PlacesData
import com.taximobility.util.NC


class LocationListView : LinearLayout, CollapseInterface {
    override fun collapsed(collapsed: Boolean) {
        isCollapsed = collapsed
        removeAllViews()
        setData(stopArray)
    }

    var isCollapsed = false

    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    lateinit var stopArray: ArrayList<PlacesData>

    init {
        orientation = LinearLayout.VERTICAL
    }

    fun setData(stopArray: ArrayList<PlacesData>) {
        this.stopArray = stopArray
        removeAllViews()
        for (i in 0 until stopArray.size) {
            if (!isCollapsed || (i == 0 || i == stopArray.size - 1))
                createStop(stopArray[i])
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        (parent as PickupDropView).forceInvalidate()
    }

    private fun createStop(stopData: PlacesData) {
        addView(AppCompatTextView(context).apply {
            isFocusableInTouchMode = false
            hint = NC.getString(R.string.search_stop_hint)
            id = stopData.id
            typeface = ResourcesCompat.getFont(context, R.font.ubuntu_medium)
            setTextColor(ContextCompat.getColor(context, R.color.textNormalColor))
            val layParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, resources.getDimension(R.dimen.stop_lay_height).toInt())
            layParams.setMargins(0, 0, 0, resources.getDimension(R.dimen.stop_space_lay).toInt())
            setPadding(resources.getDimension(R.dimen.stop_space_lay).toInt(), 0, 0, 0)
            layoutParams = layParams
            gravity = Gravity.CENTER_VERTICAL
            isSingleLine = true
            ellipsize = TextUtils.TruncateAt.MARQUEE
            marqueeRepeatLimit = -1
            post {
                isSelected = true
            }
            text = stopData.placeName
            setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        })
    }
}