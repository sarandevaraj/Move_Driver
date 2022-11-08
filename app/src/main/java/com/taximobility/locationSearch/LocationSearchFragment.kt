package com.taximobility.locationSearch

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.taximobility.R
import com.taximobility.bookingmodule.BookTaxiHomeRepository
import com.taximobility.data.apiData.PlacesDetail
import com.taximobility.util.RecyclerItemClickListener
import com.taximobility.util.SessionSave
import kotlinx.android.synthetic.main.fragment_location_search.*
import org.json.JSONArray
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [LocationSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LocationSearchFragment : Fragment(), OnLocationSearched, PlaceSearchList {

    lateinit var bookTaxiHomeRepository: BookTaxiHomeRepository
    var str: String = ""

    var mList: ArrayList<PlacesDetail> = ArrayList()

    private var mResultList: ArrayList<PlacesDetail> = ArrayList()

    override fun onItemClicked(placesDetail: PlacesDetail) {
        //No need to handle anything here
    }

    override fun setPlaceList(placeDetailResult: ArrayList<PlacesDetail>?) {
        mResultList.clear()
        if (mList != null && mList.size > 0) {
            mResultList.addAll(mList)
        }
        if (placeDetailResult != null) {
            mResultList.addAll(placeDetailResult)
        }
        mAutoCompleteAdapter.notifyDataSetChanged()
//        placeDetailResult!!.addAll(mList)
//        mAutoCompleteAdapter.submitList(placeDetailResult)
    }

    override fun setPlaceDetail(placeDetail: PlacesDetail) {
        listener?.onPlaceSelected(placeDetail)
    }

    private lateinit var onPlaceSearchedListener: OnLocationSearched
    override fun onLocationSearched(queryString: String) {
        println("onLocationSearched $queryString")
        bookTaxiHomeRepository.getFavPlaceWithFilter(queryString).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null) {
                mList.clear()
                if (it.isNotEmpty()) {
                    for (i in 0 until it.size) {
                        var placesDetail = PlacesDetail()
                        placesDetail.location_name = it[i].location_name
                        placesDetail.latitude = it[i].latitude
                        placesDetail.longtitute = it[i].longtitute
                        placesDetail.label_name = it[i].label_name
                        if (it[i].type.toInt() == 1) {
                            placesDetail.placeType = -1
                        } else if (it[i].type.toInt() == 2) {
                            placesDetail.placeType = -2
                        } else {
                            placesDetail.placeType = -3
                        }
                        placesDetail.placeId = it[i]._id.toString()
                        mList.add(placesDetail)
                    }
                }
            }
        })
//        if (queryString.length > 2)
        onPlaceSearchedListener.onLocationSearched(queryString)
//        else
//            mAutoCompleteAdapter.submitList(mList)
    }

    private var isFourSquare = false
    private var listener: SetPlaceResult? = null
    private lateinit var mAutoCompleteAdapter: PlacesAutoCompleteAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_location_search, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bookTaxiHomeRepository = BookTaxiHomeRepository(context)
        if (context is SetPlaceResult) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement SetPlaceResult")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { context ->
            imgPoweredBy.visibility = View.GONE
            if (SessionSave.getSession("isFourSquare", context) == "1") {
                isFourSquare = true
            }
            onPlaceSearchedListener = if (isFourSquare) {
                imgPoweredBy.visibility = View.GONE
                FourSquarePlaceRepository(context, this@LocationSearchFragment)
            } else {
                imgPoweredBy.visibility = View.GONE

                GooglePlaceRepository(context, this@LocationSearchFragment)
            }


            mAutoCompleteAdapter = PlacesAutoCompleteAdapter(context)
            mAutoCompleteAdapter.submitList(mResultList)
            rvLocationItems.adapter = mAutoCompleteAdapter

            rvLocationItems.addOnItemTouchListener(
                    RecyclerItemClickListener(context, RecyclerItemClickListener.OnItemClickListener { views, position ->
                        val view = activity?.currentFocus
                        if (view != null) {
                            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                            if (imm.isAcceptingText) {
                                val im = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                im.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                            }
                        }
                        if (mAutoCompleteAdapter.getItem(position) != null)
                            onPlaceSearchedListener.onItemClicked(mAutoCompleteAdapter.getItem(position))
                    })
            )
        }
    }

    private fun getFavouritesList(context: Context): ArrayList<PlacesDetail> {
        val favouritesList = ArrayList<PlacesDetail>()
        if (!SessionSave.getSession("popular_places", context).isNullOrEmpty()) {
            try {
                val popularPlaces = JSONArray(SessionSave.getSession("popular_places", context))
                for (i in 0 until popularPlaces.length()) {
                    val jo = popularPlaces.getJSONObject(i)
                    favouritesList.add(PlacesDetail().apply {
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
