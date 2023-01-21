package com.taximobility.driver

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import com.taximobility.R
import com.taximobility.driver.utils.DirverColorchange
import com.taximobility.driver.utils.DriverFontHelper
import com.taximobility.driver.utils.DriverNC
import kotlinx.android.synthetic.main.driver_canceltrip_lay.*

/**
 * This is cancel the trip
 */
class DriverShowAlertAct : MainActivityDriver() {


    private var messages: String = ""

    /**
     * setting the layout
     */
    override fun setLayout(): Int {
        return R.layout.driver_canceltrip_lay
    }

    /**
     * Initializing the component variables
     */
    override fun Initialize() {

        var moveToPlaystore = false
        val bun = intent.extras
        if (bun != null) {
            messages = bun.getString("message").toString()
            moveToPlaystore = bun.getBoolean("move_to_playstore")
        }

        DriverFontHelper.applyFont(this@DriverShowAlertAct, findViewById<View>(R.id.canceltrip))

        DirverColorchange.ChangeColor(
            (this@DriverShowAlertAct.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(
                0
            ) as ViewGroup, this@DriverShowAlertAct
        )

        message.text = messages
        button1.text = DriverNC.getString(R.string.ok)
        button1.setOnClickListener {
            if (moveToPlaystore) {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
                startActivity(intent)
            } else finish()
        }

    }
}