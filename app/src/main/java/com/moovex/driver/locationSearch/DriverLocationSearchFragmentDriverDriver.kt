package com.moovex.driver.locationSearch

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.moovex.R
import com.moovex.driver.DriverRecyclerItemClickListener
import com.moovex.driver.utils.DirverColorchange
import com.moovex.driver.utils.DriverSessionSave
import org.json.JSONArray
import java.util.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */

class DriverLocationSearchFragmentDriverDriver : Fragment(), DriverOnLocationSearched,
    DriverPlaceSearchList {
    override fun onItemClicked(driverPlacesDetail: DriverPlacesDetail) {
        //No need to handle anything here
    }

    override fun setPlaceList(placeDetailResultDriver: ArrayList<DriverPlacesDetail>?) {
        mAutoCompleteAdapterDriver.submitList(placeDetailResultDriver)
    }

    override fun setPlaceDetail(placeDetailDriver: DriverPlacesDetail) {
        listener?.onPlaceSelected(placeDetailDriver)
    }

    private lateinit var driverOnPlaceSearchedListener: DriverOnLocationSearched
    override fun onLocationSearched(queryString: String) {
        if (queryString.length > 4) driverOnPlaceSearchedListener.onLocationSearched(queryString)
        else mAutoCompleteAdapterDriver.submitList(favouritesList)
    }

    private var isFourSquare = false
    private var listener: DriverSetPlaceResult? = null
    private lateinit var mAutoCompleteAdapterDriver: DriverPlacesAutoCompleteAdapter
    private var favouritesList: ArrayList<DriverPlacesDetail> = ArrayList()
    private lateinit var rvLocationItems: RecyclerView
    private lateinit var imgPoweredBy: AppCompatImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.driver_fragment_location_search, container, false)
        DirverColorchange.ChangeColor(mView as ViewGroup, requireActivity())
        rvLocationItems = mView.findViewById(R.id.rvLocationItems)
        imgPoweredBy = mView.findViewById(R.id.imgPoweredBy)
        return mView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DriverSetPlaceResult) {
            listener = context
        } else {
            throw RuntimeException("$context must implement SetPlaceResult")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { context ->
            favouritesList = getFavouritesList(context)
            if (DriverSessionSave.getSession("isFourSquare", context) == "1") {
                isFourSquare = true
            }
            driverOnPlaceSearchedListener = if (isFourSquare) {
                imgPoweredBy.visibility = View.GONE
                DriverFourSquarePlaceRepository(
                    context, this@DriverLocationSearchFragmentDriverDriver
                )
            } else {
                imgPoweredBy.visibility = View.VISIBLE
                DriverGooglePlaceRepository(context, this@DriverLocationSearchFragmentDriverDriver)
            }

            mAutoCompleteAdapterDriver = DriverPlacesAutoCompleteAdapter(context)
            mAutoCompleteAdapterDriver.submitList(favouritesList)
            rvLocationItems.adapter = mAutoCompleteAdapterDriver

            rvLocationItems.addOnItemTouchListener(
                DriverRecyclerItemClickListener(context,
                    DriverRecyclerItemClickListener.OnItemClickListener { views, position ->
                        val view = activity?.currentFocus
                        if (view != null) {
                            val imm =
                                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                            if (imm.isAcceptingText) {
                                val im =
                                    context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                                im.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                            }
                        }
                        if (mAutoCompleteAdapterDriver.getItem(position) != null) driverOnPlaceSearchedListener.onItemClicked(
                            mAutoCompleteAdapterDriver.getItem(
                                position
                            )
                        )
                    })
            )
        }
    }

    private fun getFavouritesList(context: Context): ArrayList<DriverPlacesDetail> {
        val favouritesList = ArrayList<DriverPlacesDetail>()
        if (!DriverSessionSave.getSession("popular_places", context).isNullOrEmpty()) {
            try {
                val popularPlaces =
                    JSONArray(DriverSessionSave.getSession("popular_places", context))
                for (i in 0 until popularPlaces.length()) {
                    val jo = popularPlaces.getJSONObject(i)
                    favouritesList.add(DriverPlacesDetail().apply {
                        setLabel_name(jo.getString("label_name"))
                        setLatitude(jo.getDouble("latitude"))
                        setLongtitute(jo.getDouble("longtitute"))
                        setLocation_name(jo.getString("location_name"))
                        setAndroid_image_unfocus(jo.getString("android_icon"))
                        setPlaceId("")
                        setPlaceType(1)
                    })
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return favouritesList
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
