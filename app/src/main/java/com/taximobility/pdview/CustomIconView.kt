package com.taximobility.pdview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.taximobility.R
import com.taximobility.util.AndroidUtils.Companion.dpToPx

class CustomIconView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0) : View(context, attrs, defStyle), CollapseInterface {
    override fun collapsed(collapsed: Boolean) {
        if (collapsed && expandedLength > 2)
            length = 2
        else
            length = expandedLength
        invalidate()
    }

    private var paint: Paint


    private var singleLayHeight: Int
    private var spaceBetweenLayout: Int
    private var pointHeight: Int
    private var canvasHeight: Int
    private var marginTop: Int
    private var linkLineHeight: Int
    private var linkLineWidth: Int
    private var length = 0
    private var expandedLength = 0
    private var includeUnderline = false

    init {
        var underlineHeight = 0
        if (includeUnderline)
            underlineHeight = resources.getDimension(R.dimen.underline_view_height).toInt()

        singleLayHeight = resources.getDimension(R.dimen.stop_lay_height).toInt() + underlineHeight
        spaceBetweenLayout = resources.getDimension(R.dimen.stop_space_lay).toInt()
        pointHeight = resources.getDimension(R.dimen.stop_icon_lay).toInt()
        canvasHeight = (singleLayHeight * length) + (spaceBetweenLayout * (length - 1)) + (underlineHeight * (length - 1))
        marginTop = (singleLayHeight / 2) - (pointHeight / 2)
        linkLineHeight = singleLayHeight + spaceBetweenLayout - pointHeight + underlineHeight
        linkLineWidth = dpToPx(2, context)
//        println("pppppppppp singleLayHeight : ${pxToDp(singleLayHeight, context)} spaceBetweenLayout: ${pxToDp(spaceBetweenLayout, context)} +" +
//                "pointHeight: ${pxToDp(pointHeight, context)} canvasHeight: ${pxToDp(canvasHeight, context)} marginTop: ${pxToDp(marginTop, context)} linkLineHeight: ${pxToDp(linkLineHeight, context)} ${pxToDp(linkLineWidth, context)}")
        paint = Paint()


    }


    fun setData(length: Int) {
        this.length = length
        expandedLength = length
//        this.includeUnderline = includeUnderline
        invalidate()
    }

    override fun onDraw(mCanvas: Canvas?) {
        super.onDraw(mCanvas)
        mCanvas?.let { canvas ->
            val widthoffset = (width / 2)

            var yOffset = marginTop

            for (i in 1..length) {

                if (i == 1) {
                    paint.color = ContextCompat.getColor(context, R.color.pickup_green)
                    canvas.drawCircle(widthoffset.toFloat(), yOffset.toFloat() + (pointHeight / 2), (pointHeight / 2).toFloat(), paint)

                    yOffset += pointHeight
                    if (i != length) {
                        canvas.drawRect(Rect(widthoffset - (linkLineWidth / 2), yOffset, widthoffset + (linkLineWidth / 2), yOffset + (linkLineHeight / 2)), paint)

                        yOffset += linkLineHeight / 2
                        paint.color = ContextCompat.getColor(context, R.color.pickup_red)
                        canvas.drawRect(Rect(widthoffset - (linkLineWidth / 2), yOffset, widthoffset + (linkLineWidth / 2), yOffset + (linkLineHeight / 2)), paint)
                        yOffset += linkLineHeight / 2
                    }


                } else {


                    if (i == length)
                        canvas.drawRect(Rect(widthoffset - (pointHeight / 2), yOffset, widthoffset + (pointHeight / 2), yOffset + pointHeight), paint)
                    else
                        canvas.drawCircle(widthoffset.toFloat(), yOffset.toFloat() + (pointHeight / 2), (pointHeight / 2).toFloat(), paint)
                    yOffset += pointHeight
                    if (i != length) {
                        canvas.drawRect(Rect(widthoffset - (linkLineWidth / 2), yOffset, widthoffset + (linkLineWidth / 2), yOffset + linkLineHeight), paint)

                        yOffset += linkLineHeight
                    }

                }

            }


        }
//        layoutParams = RelativeLayout.LayoutParams(dpToPx(50, context), canvasHeight)

//

    }

}