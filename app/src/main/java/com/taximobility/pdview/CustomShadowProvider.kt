package com.taximobility.pdview

import android.graphics.Outline
import android.os.Build
import androidx.annotation.RequiresApi
import android.view.View
import android.view.ViewOutlineProvider


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CustomShadowProvider internal constructor(val radius: Float) : ViewOutlineProvider() {

    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(0, 0, view.width, view.height, radius)
    }
}