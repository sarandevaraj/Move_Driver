package com.taximobility

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.taximobility.util.Colorchange
import com.taximobility.util.FontHelper
import com.taximobility.util.NC


/**
 * This is cancel the trip
 */
class ShowAlertAct : AppCompatActivity() {


    var messages: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_alert)
        Initialize()
    }

    /**
     * Initializing the component variables
     */
    fun Initialize() {
        var moveToPlaystore = false
        val bun = intent.extras
        if (bun != null) {
            messages = bun.getString("message").toString()
            moveToPlaystore = bun.getBoolean("move_to_playstore")
        }

        FontHelper.applyFont(this@ShowAlertAct, findViewById<View>(R.id.canceltrip))

        Colorchange.ChangeColor((this@ShowAlertAct
                .findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup, this@ShowAlertAct)
        var button = findViewById<Button>(R.id.button1)
        findViewById<TextView>(R.id.message).text = messages
        button.text = NC.getString(R.string.ok)
        button.setOnClickListener {
            if (moveToPlaystore) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName))
                startActivity(intent)
            } else
                finish()
        }

    }


}