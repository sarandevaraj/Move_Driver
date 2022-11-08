package com.taximobility.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.util.DisplayMetrics
import android.widget.Toast


class AndroidUtils {
    companion object {
        fun copyText(con: Context, text: String) {

            val clipboard = con.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("label", text)
        //    clipboard!!.primaryClip = clip
            Toast.makeText(con, "Text Copied", Toast.LENGTH_SHORT).show()
        }


        fun dpToPx(dp: Int, con: Context): Int {
            val displayMetrics = con.resources.displayMetrics
            return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }

        fun pxToDp(px: Int, con: Context): Int {
            val displayMetrics = con.resources.displayMetrics
            return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }
    }
}