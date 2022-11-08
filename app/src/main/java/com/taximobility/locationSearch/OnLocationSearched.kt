package com.taximobility.locationSearch

import com.taximobility.data.apiData.PlacesDetail

interface OnLocationSearched {
    fun onLocationSearched(queryString: String)
    fun onItemClicked(placesDetail: PlacesDetail)
}