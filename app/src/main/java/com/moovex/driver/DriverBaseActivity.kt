package com.moovex.driver

import androidx.appcompat.app.AppCompatActivity
import com.moovex.service.FirebaseService

open class DriverBaseActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        var lastInteractionTime = System.currentTimeMillis()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        lastInteractionTime = System.currentTimeMillis()
        println("user interacted $lastInteractionTime")
    }

    override fun onResume() {
        super.onResume()
        if (this !is DriverChatWebviewAct) {
            FirebaseService.activity = null
        }
    }
}