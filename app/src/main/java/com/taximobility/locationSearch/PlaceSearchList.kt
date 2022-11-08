package com.taximobility.locationSearch

import com.taximobility.data.apiData.PlacesDetail
import java.util.*

interface PlaceSearchList {
    fun setPlaceList(placeDetailResult: ArrayList<PlacesDetail>?)
    fun setPlaceDetail(placeDetail: PlacesDetail)
}