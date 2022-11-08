package com.taximobility.bookingmodule.pickDropLoc

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.taximobility.R
import com.taximobility.bookingmodule.favourite.FavouriteDialog
import com.taximobility.bookingmodule.Interface.PickDropSetListener
import com.taximobility.data.apiData.PlacesDetail
import com.taximobility.databinding.SearchLocationBinding
import com.taximobility.features.CToast
import com.taximobility.locationSearch.AddStopActivity
import com.taximobility.locationSearch.PickupDropSearchActivity
import com.taximobility.locationSearch.PlacesData
import com.taximobility.util.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.search_location.view.*
import java.util.*

class SearchLocationFrag : Fragment(), View.OnTouchListener {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: SearchLocationBinding

    //to set favourite icon visibility touch pick or drop
    private var locFocus: String = PickupPlace

    //to check pickup or drop favourite click
    private var pickDropFavClick: Boolean = false
    //to check pick and drop location click true for pickup false for drop
    private var pickDropClick: Boolean = true

    //set recent places in Places data
    private var placeArray = ArrayList<PlacesData>()

    // dialog frag for favourite place type
    private val dialogFragment = FavouriteDialog()

    private var pickPlaceData: PlacesData? = null
    private var dropPlacesData: PlacesData? = null

    private lateinit var listener: PickDropSetListener
    private var mList: ArrayList<PlacesData> = ArrayList()
    private var locationRequestedBy = ""
    var pickuplatlng: LatLng = LatLng(0.0, 0.0)
    var droplatlng: LatLng = LatLng(0.0, 0.0)
    var pickupLocTxt: String = ""
    var dropLocTxt: String = ""
    //Favourite place type for pickup and drop 1 means fav 0 means unFav
    private var pickFavPlaceType = "0"
    private var dropFavPlaceType = "0"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_location, container, false)
        searchViewModel = ViewModelProviders.of(this@SearchLocationFrag).get(SearchViewModel::class.java)
        binding.apply {
            myViewModel = searchViewModel
            lifecycleOwner = this@SearchLocationFrag
            executePendingBindings()
        }
        initialize()
        return binding.root
    }


    fun getDropLatLng(): LatLng {
        return droplatlng
    }

    private fun initialize() {
        // binding.pickupCard.setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.white))
        //  binding.dropCard.setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.linebottom_light))
        FontHelper.applyFont(requireActivity(), requireActivity().findViewById(R.id.relativelay))

        dialogFragment.isCancelable = false
        setFavIconVisibility()
        locVisibility()
        searchViewModel.pickDropclick.observe(this, Observer {
            if (it != null)
                pickDropClick = it
            if (it != null && it) {
                setPickupCardElevation()
                pickupClicked()
            } else {
                setDropCardElevation()
                if (dropLocTxt == "" || dropLocTxt!="")
                    dropClicked("")
            }
            setFavIconVisibility()
        })

        searchViewModel.favClick.observe(this, Observer {
            if (it != null) {
                pickDropFavClick = it
                if (it) {
                    val tag: String = binding.root.pic_loc_select.tag.toString()
                    if (tag.equals("ic_favorite_unselect", ignoreCase = true) && !pickupLocTxt.equals(NC.getString(R.string.fetching_address), ignoreCase = true)) {
                        dialogFragment.arguments = Bundle().apply {
                            putString("p_favourite_place", pickupLocTxt)
                            putDouble("p_fav_latitude", pickuplatlng.latitude)
                            putDouble("p_fav_longtitute", pickuplatlng.longitude)
                        }
                        dialogFragment.show(childFragmentManager, "dialog")
                    } else {
                        CToast.ShowToast(requireActivity(), NC.getString(R.string.fav_alert))
                    }
                } else {
                    val tag: String = binding.root.drop_loc_select.tag.toString()
                    if (tag.equals("ic_favorite_unselect", ignoreCase = true) && !dropLocTxt.equals(NC.getString(R.string.fetching_address), ignoreCase = true)) {
                        dialogFragment.arguments = Bundle().apply {
                            putString("p_favourite_place", dropLocTxt)
                            putDouble("p_fav_latitude", droplatlng.latitude)
                            putDouble("p_fav_longtitute", droplatlng.longitude)
                        }
                        dialogFragment.show(childFragmentManager, "dialog")
                    } else {
                        CToast.ShowToast(requireActivity(), NC.getString(R.string.fav_alert))
                    }
                }
            }
        })

        dialogFragment.fav.observe(this, Observer {
            if (it != null && it == 1) {
                setFavIcon()
            }
        })

        binding.picLocSelect.setOnClickListener {
            searchViewModel.favIconClick(PickupPlace)
        }

        binding.dropLocSelect.setOnClickListener {
            searchViewModel.favIconClick(DropPlace)
        }

        searchViewModel.recentPlaceClick.observe(this, Observer {
            var placeClickPos = 0
            if (it != null && it != 0) {
                placeClickPos = it - 1
            } else if (it != null) {
                placeClickPos = it
            }
            if (locFocus == DropPlace) {
                setPickupDropData(placeArray[placeClickPos].id, placeArray[placeClickPos].lat, placeArray[placeClickPos].lng, placeArray[placeClickPos].placeName, placeArray[placeClickPos].placeId, DropPlace, "")
            } else {
                setPickupDropData(placeArray[placeClickPos].id, placeArray[placeClickPos].lat, placeArray[placeClickPos].lng, placeArray[placeClickPos].placeName, placeArray[placeClickPos].placeId, PickupPlace, "")
            }
            setFavouriteUnselectIcon()
        })

        binding.root.txt_pickup.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    setFavIconVisibility()
                }
            }
        })


        binding.root.txt_drop.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    setFavIconVisibility()
                }
            }
        })
        binding.txtPickup.setOnTouchListener(this)
        binding.txtDrop.setOnTouchListener(this)

        searchViewModel.getAllPastBookingPlaces().observe(this, Observer {
            if (it != null) {
                if (it.isNotEmpty()) {
                    placeArray.clear()
                    for (i in 0 until it.size) {
                        val placesData = PlacesData(it[i]._id.toInt(), it[i].latitude, it[i].longtitute, it[i].location_name, "", it[i].type)
                        placeArray.add(placesData)
                    }
                    searchViewModel.setRecentPlaces(placeArray)
                }
            }
        })
    }

    fun setSearchFragmentListener(pickupDropSet: PickDropSetListener) {
        this.listener = pickupDropSet
    }

    /**
     * Recent places visibility
     */
    fun locVisibility() {
        searchViewModel.recentVisible.value = placeArray.size
        searchViewModel.pickupVisible.value = 1
        searchViewModel.dropVisible.value = 1
    }

    /**
     * Recent places invisibility
     */
    fun locInVisibility() {
        searchViewModel.recentVisible.value = 0
        searchViewModel.pickupVisible.value = 0
        searchViewModel.dropVisible.value = 0
    }

    /**
     * Favourite icon visibility
     */
    private fun setFavIconVisibility() {
        if (locFocus == PickupPlace && binding.root.txt_pickup.text.toString().isNotEmpty()) {
            binding.root.pic_loc_select.visibility = View.VISIBLE
            binding.root.drop_loc_select.visibility = View.INVISIBLE
        } else if (locFocus == DropPlace && binding.root.txt_drop.text.toString().isNotEmpty()) {
            binding.root.drop_loc_select.visibility = View.VISIBLE
            binding.root.pic_loc_select.visibility = View.INVISIBLE
        }
    }


    /**
     * Pickup location perform click
     */
    private fun pickupClicked() {
        if (pickupLocTxt != NC.getString(R.string.fetching_address)) {
            if (SessionSave.getSession(TaxiUtil.IS_STOP_ENABLED, context, false) && mList != null && mList.size >= 3) {
                val intent = Intent(requireActivity(), AddStopActivity::class.java)
                intent.putParcelableArrayListExtra(BUNDLE_PICKUP_DROP_ADDRESS, mList)
                startActivityForResult(intent, TaxiUtil.LocationResult)
            } else {
                val b = Bundle()
                b.putString("type", PickupPlace)
                val i = Intent(requireActivity(), PickupDropSearchActivity::class.java)
                b.putParcelableArrayList(BUNDLE_PICKUP_DROP_ADDRESS, getStopPoints())
                if (droplatlng != null) {
                    val dropObj = PlacesDetail()
                    dropObj.setLatitude(droplatlng.latitude)
                    dropObj.setLongtitute(droplatlng.longitude)
                    dropObj.setLocation_name(dropLocTxt)
                    dropObj.setFavPlaceType(dropFavPlaceType)
                    b.putParcelable("drop_obj", dropObj)
                }
                if (pickuplatlng != null) {
                    val pickupObj = PlacesDetail()
                    pickupObj.setLatitude(pickuplatlng.latitude)
                    pickupObj.setLongtitute(pickuplatlng.longitude)
                    pickupObj.setLocation_name(pickupLocTxt)
                    pickupObj.setFavPlaceType(pickFavPlaceType)
                    b.putParcelable("pickup_obj", pickupObj)
                }
                i.putExtras(b)
                startActivityForResult(i, TaxiUtil.LocationResult)
            }
        }
    }

    /**
     * Drop location perform click
     */
    fun dropClicked(str: String) {
        if (dropLocTxt != NC.getString(R.string.fetching_address)) {
            if (SessionSave.getSession(TaxiUtil.IS_STOP_ENABLED, context, false) && mList != null && mList.size >= 3) {
                val intent = Intent(requireActivity(), AddStopActivity::class.java)
                when (str) {
                    "1" -> intent.putExtra("Package", "1")
                    else -> intent.putExtra("Package", "")
                }
                intent.putParcelableArrayListExtra(BUNDLE_PICKUP_DROP_ADDRESS, mList)
                startActivityForResult(intent, TaxiUtil.LocationResult)
            } else if (pickupLocTxt.isNotEmpty()) {
                locationRequestedBy = DropPlace
                val b = Bundle()
                b.putString("type", DropPlace)
                when (str) {
                    "1" -> b.putString("Package", "1")
                    else -> b.putString("Package", "")
                }
                b.putParcelableArrayList(BUNDLE_PICKUP_DROP_ADDRESS, getStopPoints())
                if (pickuplatlng != null) {
                    val pickupObj = PlacesDetail()
                    pickupObj.setLatitude(pickuplatlng.latitude)
                    pickupObj.setLongtitute(pickuplatlng.longitude)
                    pickupObj.setLocation_name(pickupLocTxt)
                    pickupObj.setFavPlaceType(pickFavPlaceType)
                    b.putParcelable("pickup_obj", pickupObj)
                }
                if (droplatlng != null) {
                    val dropObj = PlacesDetail()
                    dropObj.setLatitude(droplatlng.latitude)
                    dropObj.setLongtitute(droplatlng.longitude)
                    dropObj.setLocation_name(dropLocTxt)
                    dropObj.setFavPlaceType(dropFavPlaceType)
                    b.putParcelable("drop_obj", dropObj)
                }
                val i = Intent(requireActivity(), PickupDropSearchActivity::class.java)
                i.putExtras(b)
                startActivityForResult(i, TaxiUtil.LocationResult)
            }
        }
    }


    fun getStopPoints(): ArrayList<PlacesData> {
        if (mList != null) {
            return if (mList.size == 0) {
                if (pickuplatlng.latitude != 0.0) {
                    mList.add(PlacesData(0, pickuplatlng.latitude, pickuplatlng.longitude, pickupLocTxt, "", PickupPlace, pickFavPlaceType))
                }
                if (droplatlng.latitude != 0.0) {
                    mList.add(PlacesData(0, droplatlng.latitude, droplatlng.longitude, dropLocTxt, "", DropPlace, dropFavPlaceType))
                }
                mList
            } else
                mList
        } else {
            mList = ArrayList()
            mList.add(PlacesData(0, pickuplatlng.latitude, pickuplatlng.longitude, pickupLocTxt, "", "", pickFavPlaceType))
            return mList
        }
    }

    /**
     * Pickup location Elevation
     */
    fun setPickupCardElevation() {
        if (pickDropClick) {
            with(binding) {
                //                dropCard.apply {
//                    //setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.linebottom_light))
//                    dropCard.cardElevation = resources.getDimension(R.dimen.dp_1)
//                }
//                pickupCard.apply {
//                    setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.white))
//                    pickupCard.cardElevation = resources.getDimension(R.dimen.dp_5)
//                }
            }
            if (pickupLocTxt != "" && pickupLocTxt != NC.getString(R.string.fetching_address)) {
                listener.pickupListener()
            }
        }
    }

    /**
     * Drop location Elevation
     */
    fun setDropCardElevation() {
        if (!pickDropClick) {
            with(binding) {
                //                pickupCard.apply {
//                    setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.linebottom_light))
//                    cardElevation = resources.getDimension(R.dimen.dp_1)
//                }
//                dropCard.apply {
//                    setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.white))
//                    cardElevation = resources.getDimension(R.dimen.dp_5)
//                }
            }

            if (dropLocTxt != "" && dropLocTxt != NC.getString(R.string.fetching_address)) {
                listener.dropListener()
            }
        }
    }

    /**
     * Set favourite location icon
     */
    fun setFavIcon() {
        if (locFocus == PickupPlace) {
            pickFavPlaceType = "1"
            binding.picLocSelect.tag = "ic_favorite_select"
            binding.picLocSelect.setImageResource(R.drawable.ic_favorite_select)
        }    else {
            dropFavPlaceType = "1"
            binding.dropLocSelect.tag = "ic_favorite_select"
            binding.dropLocSelect.setImageResource(R.drawable.ic_favorite_select)
        }
    }

    /**
     * Set unfavourite location icon
     */
    fun setFavouriteUnselectIcon() {
        if (locFocus == PickupPlace) {
            pickFavPlaceType = "0"
            with(binding) {
                picLocSelect.apply {
                    tag = "ic_favorite_unselect"
                    setImageResource(R.drawable.ic_favorite_unselect)
                }
            }
        } else {
            dropFavPlaceType = "0"
            with(binding) {
                dropLocSelect.apply {
                    tag = "ic_favorite_unselect"
                    setImageResource(R.drawable.ic_favorite_unselect)
                }
            }
        }
    }

    fun setPickupDropData(id: Int, lat: Double, lng: Double, address: String, placeId: String, addrType: String, favLocType: String) {
        if (locFocus == PickupPlace) {
            pickPlaceData = PlacesData(id, lat, lng, address, placeId, addrType, favLocType)
            pickFavPlaceType = favLocType
            setPickLoc(address)
            setPickLatLng(LatLng(lat, lng))
        } else {
            dropPlacesData = PlacesData(id, lat, lng, address, placeId, addrType, favLocType)
            dropFavPlaceType = favLocType
            setDropLoc(address)
            setDropLatLng(LatLng(lat, lng))
        }
        if (favLocType == "1")
            setFavIcon()
        else
            setFavouriteUnselectIcon()
        searchViewModel.setPickupDropLocation(address, locFocus)
    }

    fun setPickLatLng(pickLatLng: LatLng) {
        if (pickLatLng.latitude != 0.0) {
            this.pickuplatlng = pickLatLng
            pickPlaceData = PlacesData(0, pickuplatlng.latitude, pickuplatlng.longitude, pickupLocTxt, "", PickupPlace, pickFavPlaceType)
            if (pickupLocTxt.isNotEmpty())
                searchViewModel.setPickupLoc(pickupLocTxt)
            listener.pickUpSet(pickuplatlng.latitude, pickuplatlng.longitude, pickupLocTxt)
            if (mList.size > 0)
                mList[0] = PlacesData(0, pickuplatlng.latitude, pickuplatlng.longitude, pickupLocTxt, "", PickupPlace, pickFavPlaceType)
        } else {
            if (pickupLocTxt == "")
                searchViewModel.setPickupLoc(NC.getString(R.string.fetching_address))
        }
    }

    fun setDropLatLng(dropLatLng: LatLng) {
        if (dropLatLng.latitude != 0.0) {
            this.droplatlng = dropLatLng
            dropPlacesData = PlacesData(1 + Random().nextInt(), droplatlng.latitude, droplatlng.longitude, dropLocTxt, "", DropPlace, dropFavPlaceType)
            if (dropLocTxt.isNotEmpty())
                searchViewModel.setDropLoc(dropLocTxt)
            listener.dropSet(droplatlng.latitude, droplatlng.longitude, dropLocTxt, locFocus)
            if (mList.size > 1)
                mList[mList.size - 1] = PlacesData(1 + Random().nextInt(), droplatlng.latitude, droplatlng.longitude, dropLocTxt, "", DropPlace, dropFavPlaceType)
        }
    }


    private fun setPickLoc(address: String) {
        this.pickupLocTxt = address
    }

    private fun setDropLoc(address: String) {
        this.dropLocTxt = address
    }

    fun getLatLngPoints(): ArrayList<LatLng> {
        val arrayList = ArrayList<LatLng>()
        if (mList != null && mList.size > 1) {
            for (i in mList.indices) {
                arrayList.add(LatLng(mList[i].lat, mList[i].lng))
            }
        } else {
            arrayList.add(LatLng(pickuplatlng.latitude, pickuplatlng.longitude))
            if (droplatlng != null)
                arrayList.add(LatLng(droplatlng.latitude, droplatlng.longitude))
        }

        return arrayList
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var setForPickup = false
        var result: String? = ""
        var placeType: String = ""
        var packageStr: String = ""
        try {
            var lat = 0.0
            var lng = 0.0
            if (data != null) {
                val res = data.extras
                if (data.getStringExtra(BUNDLE_BOOKING_ADDRESS_TYPE) != null)
                    placeType = data.getStringExtra(BUNDLE_BOOKING_ADDRESS_TYPE) ?: ""
                result = res!!.getString("param_result") ?: ""
                lat = res.getDouble("lat")
                lng = res.getDouble("lng")
                setForPickup = res.getBoolean("set_for_pickup")
                mList = res.getParcelableArrayList(BUNDLE_PICKUP_DROP_ADDRESS)!!
                if (placeType != "" && placeType == "1") {
                    setFavIcon()
                } else {
                    setFavouriteUnselectIcon()
                }
                if (res.getString("Package") != null) {
                    packageStr = res.getString("Package").toString()
                }

            } else {
                //to avoid drop dragging option disable until drop location set first time
                if (dropLocTxt == "") {
                    pickDropClick = true
                    locFocus = PickupPlace
                    setPickupCardElevation()
                }
            }
            if (setForPickup && result != null && result.isNotEmpty()) {
                pickupLocTxt = result
                val p = LatLng(lat, lng)
                setPickLatLng(p)
            } else if (result != null && result.isNotEmpty()) {
                dropLocTxt = result
                var p: LatLng? = null
                if (lat != 0.0)
                    p = LatLng(lat, lng)
                if (p != null)
                    setDropLatLng(p)
            } else {
                if (mList != null) {
                    val placesData = mList[0]
                    pickupLocTxt = placesData.placeName
                    if (placesData.favPlaceType?.isNotEmpty() == true && placesData.favPlaceType == "1")
                        setFavIcon()
                    else
                        setFavouriteUnselectIcon()
                    setPickLatLng(LatLng(placesData.lat, placesData.lng))
                    if (mList.size >= 2) {
                        val dropPlaceData = mList[mList.size - 1]
                        dropLocTxt = dropPlaceData.placeName
                        if (dropPlaceData.favPlaceType?.isNotEmpty() == true && dropPlaceData.favPlaceType == "1")
                            setFavIcon()
                        else
                            setFavouriteUnselectIcon()
                        setDropLatLng(LatLng(dropPlaceData.lat, dropPlaceData.lng))
                    } else if (mList.size == 1) {
                        locFocus = PickupPlace
                        searchViewModel.setPickupDropLocation(mList[0].placeName, locFocus)
                        searchViewModel.setDropLoc("")
                        droplatlng = LatLng(0.0, 0.0)
                        dropLocTxt = ""
                    }
                }
            }
            if (packageStr == "1") {
                listener.kmRestrictListener()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (view) {
            binding.root.txt_pickup -> {
                if (dropLocTxt != NC.getString(R.string.fetching_address)) {
                    locFocus = PickupPlace
                    when (motionEvent.action) {
                        MotionEvent.ACTION_UP -> {
//                            if (binding.pickupCard.cardBackgroundColor.defaultColor == ContextCompat.getColor(requireActivity(), R.color.white)) {
//                                locationRequestedBy = PickupPlace
//                                pickupClicked()
//                            }
                            searchViewModel.pickupClick()
                        }
                    }
                }
            }
            binding.root.txt_drop -> {
                if (pickupLocTxt != NC.getString(R.string.fetching_address)) {
                    locFocus = DropPlace
                    when (motionEvent.action) {
                        MotionEvent.ACTION_UP -> {
//                            if (binding.dropCard.cardBackgroundColor.defaultColor == ContextCompat.getColor(requireActivity(), R.color.white)) {
//                                locationRequestedBy = DropPlace
//                                if (dropLocTxt != "")
//                                    dropClicked("")
//                            }
                            searchViewModel.dropClick()
                        }
                    }
                }
            }
        }
        return true
    }

}