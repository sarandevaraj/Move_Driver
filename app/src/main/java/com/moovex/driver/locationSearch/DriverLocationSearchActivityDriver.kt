package com.moovex.driver.locationSearch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.moovex.R
import com.moovex.driver.DriverBaseActivity
import com.moovex.driver.utils.DirverColorchange
import com.moovex.driver.utils.DriverSessionSave
import kotlinx.android.synthetic.main.driver_activity_location_search.*

class DriverLocationSearchActivityDriver : DriverBaseActivity(), DriverSetPlaceResult {
    private var isFourSquare = false

    private lateinit var driverLocationSearchFragment: DriverLocationSearchFragmentDriverDriver
    private lateinit var listener: DriverOnLocationSearched
    private lateinit var edLocation: EditText
    override fun onPlaceSelected(driverPlacesDetail: DriverPlacesDetail) {
        setResult(RESULT_OK, Intent().apply {
            putExtras(Bundle().apply {
                putExtra("param_result", driverPlacesDetail.location_name)
                putExtra("lat", driverPlacesDetail.latitude)
                putExtra("lng", driverPlacesDetail.longtitute)
            })
        })
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.driver_activity_location_search)
        edLocation = findViewById(R.id.edLocation)
        DirverColorchange.ChangeColor(
            (this@DriverLocationSearchActivityDriver.findViewById(android.R.id.content) as ViewGroup).getChildAt(
                0
            ) as ViewGroup, this@DriverLocationSearchActivityDriver
        )
        driverLocationSearchFragment = DriverLocationSearchFragmentDriverDriver()
        listener = driverLocationSearchFragment
        supportFragmentManager.beginTransaction().add(R.id.searchFrag, driverLocationSearchFragment)
            .commitNow()

        isFourSquare = DriverSessionSave.getSession("isFourSquare", this) == "1"
    }

    override fun onResume() {
        super.onResume()
        val textWatcher = object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.onLocationSearched(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                imgClearSearch.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable) {
                imgClearSearch.visibility = View.VISIBLE
            }
        }
        edLocation.addTextChangedListener(textWatcher)

        imgClearSearch.setOnClickListener {
            edLocation.setText("")
        }

        imgBackButton.setOnClickListener {
            onBackPressed()

        }

    }
}
