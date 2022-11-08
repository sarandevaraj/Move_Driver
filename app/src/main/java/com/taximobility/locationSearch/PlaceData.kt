package com.taximobility.locationSearch

data class PlaceData(var placeName: String, var placeId:
String, var address: String, var placeImageUrl: String
                     , val placeType: Int = 0)