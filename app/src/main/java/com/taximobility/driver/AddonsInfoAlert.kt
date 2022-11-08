package com.taximobility.driver

import android.app.Activity
import android.graphics.Point
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.taximobility.R
import com.taximobility.driver.adapter.AddonsAdapter
import com.taximobility.driver.data.apiData.AddonsData
import com.taximobility.driver.utils.DirverColorchange.ChangeColor
import com.taximobility.util.FontHelper


class AddonsInfoAlert() {
    fun AddonsInfo(context: Activity, addonsList: ArrayList<AddonsData>) {

        val mBottomSheetDialog = BottomSheetDialog(context)
        val sheetView: View = context.getLayoutInflater().inflate(R.layout.driver_addon_dialog, null)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.setCancelable(true)
        mBottomSheetDialog.show()
        ChangeColor(sheetView.findViewById(R.id.inner_content), context)
        FontHelper.applyFont(context, sheetView)
        val rc_addons = sheetView.findViewById<RecyclerView>(R.id.rc_addons)
        val layoutManagercancel = LinearLayoutManager(context)
        rc_addons.setLayoutManager(layoutManagercancel)
        val addonAdapter = AddonsAdapter(context, addonsList, 1)
        rc_addons.setAdapter(addonAdapter)
        val cancel = sheetView.findViewById<TextView>(R.id.btn_cancel)
        val pointSize = Point()
        context.windowManager.defaultDisplay.getSize(pointSize)
        cancel.setOnClickListener { cancel ->

            mBottomSheetDialog.cancel()
        }


    }


}
