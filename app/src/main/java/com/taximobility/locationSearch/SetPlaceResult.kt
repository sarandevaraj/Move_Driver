package com.taximobility.locationSearch

import com.taximobility.data.apiData.PlacesDetail

interface SetPlaceResult {
    fun onPlaceSelected(placesDetail: PlacesDetail)
}