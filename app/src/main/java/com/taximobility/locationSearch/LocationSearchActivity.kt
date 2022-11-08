package com.taximobility.locationSearch

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.taximobility.R
import com.taximobility.bookingmodule.BookTaxiHomeViewModel
import com.taximobility.bookingmodule.LocationData
import com.taximobility.bookingmodule.adapter.LocationListAdapter
import com.taximobility.data.apiData.PlacesDetail
import com.taximobility.databinding.ActivityLocationSearchBinding
import com.taximobility.util.*
import kotlinx.android.synthetic.main.activity_location_search.*
import org.json.JSONException
import org.json.JSONObject

class LocationSearchActivity : AppCompatActivity(), SetPlaceResult, LocationListAdapter.DeleteClickListener {
    private var isFourSquare = false

    private lateinit var locationSearchFragment: LocationSearchFragment
    private lateinit var listener: OnLocationSearched

    lateinit var mAdapter: LocationListAdapter
    var favList = ArrayList<LocationData>()
    var popList = ArrayList<LocationData>()
    var recList = ArrayList<LocationData>()
    var locationListData = ArrayList<LocationData>()
    lateinit var viewModel: BookTaxiHomeViewModel
    lateinit var binding: ActivityLocationSearchBinding


    override fun onPlaceSelected(placesDetail: PlacesDetail) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtras(Bundle().apply {
                putExtra(BUNDLE_STOP_ADDRESS, placesDetail.location_name)
                putExtra(BUNDLE_STOP_ID, id)
                putExtra(BUNDLE_STOP_LAT, placesDetail.latitude)
                putExtra(BUNDLE_STOP_LNG, placesDetail.longtitute)
                putExtra(BUNDLE_STOP_PID, placesDetail.placeId)
                putExtra(BUNDLE_BOOKING_ADDRESS_TYPE, placesDetail.favPlaceType)
            })
        })
        finish()
    }

    lateinit var id: String
    var addrType: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location_search)

        viewModel = ViewModelProviders.of(this).get(BookTaxiHomeViewModel::class.java)

        binding.apply {
            locationLists = viewModel
            lifecycleOwner = this@LocationSearchActivity
            executePendingBindings()
        }
        id = intent.getStringExtra(BUNDLE_STOP_ID)!!
        if (intent.getStringExtra(BUNDLE_BOOKING_ADDRESS_TYPE) != null) {
            addrType = intent.getStringExtra(BUNDLE_BOOKING_ADDRESS_TYPE)!!
        }
        locationSearchFragment = LocationSearchFragment()
        listener = locationSearchFragment

        isFourSquare = SessionSave.getSession("isFourSquare", this) == "1"
        val ss = LinearLayoutManager(this@LocationSearchActivity)
        ss.orientation = LinearLayoutManager.VERTICAL
        locationList.layoutManager = ss
        mAdapter = LocationListAdapter(this@LocationSearchActivity, locationListData, this)
        viewModel.getAllFavPopRecPlaces().observe(this, Observer {
            if (it != null) {
                clearAllList()
                var listSize = it.size
                for (i in 0 until listSize) {
                    if (it[i].type == "1") {
                        favList.add(it[i])
                    } else if (it[i].type == "2") {
                        popList.add(it[i])
                    } else {
                        recList.add(it[i])
                    }
                }
                updateListAdapter()
            }
        })
        locationList.adapter = mAdapter
        supportFragmentManager.beginTransaction().add(R.id.searchFrag, locationSearchFragment).commitNow()
    }

    fun clearAllList() {
        popList = ArrayList()
        favList = ArrayList()
        recList = ArrayList()
        locationListData = ArrayList()
    }

    private fun updateListAdapter() {
        if (favList.size > 0) {
            locationListData.add(LocationData(0, "", "", 0.0, 0.0, 0.0, 0.0,
                    "", NC.getString(R.string.favourite), "", "", "", FavouritePlace))
            locationListData.addAll(favList)
        }
        if (popList.size > 0) {
            locationListData.add(LocationData(0, "", "", 0.0, 0.0, 0.0, 0.0,
                    "", NC.getString(R.string.popular), "", "", "", PopularPlace))
            locationListData.addAll(popList)
        }
        if (recList.size > 0) {
            locationListData.add(LocationData(0, "", "", 0.0, 0.0, 0.0, 0.0,
                    "", NC.getString(R.string.recent), "", "", "", RecentPlace))
            locationListData.addAll(recList)
        }
        mAdapter.updateList(locationListData)
    }


    override fun deleteClick(locationData: LocationData) {
        viewModel.deleteFavourite(locationData.location_name)
        val j = JSONObject()
        try {
            j.put("passenger_id", "" + SessionSave.getSession(PASS_ID, this))
            j.put("p_favourite_id", "" + locationData._id)
            viewModel.deleteFavouriteApiCall(j)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    override fun itemClick(pos: Int) {

        val item = mAdapter.getItem(pos)
        TaxiUtil.Latitude = item.latitude
        TaxiUtil.Longitude = item.longtitute
        TaxiUtil.Address = "${item.location_name}"
        onPlaceSelected(PlacesDetail().apply {
            location_name = "${item.location_name}"
            label_name = item.label_name
            latitude = item.latitude
            longtitute = item.longtitute
            favPlaceType = item.type
            this.placeId = item._id.toString()
        })
    }

    override fun onResume() {
        super.onResume()
        val textWatcher = object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length > 2) {
                    searchFrag.visibility = View.VISIBLE
                    locationList.visibility = View.GONE
                    listener.onLocationSearched(s.toString())
                } else {
                    locationList.visibility = View.VISIBLE
                    searchFrag.visibility = View.GONE
                }
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
