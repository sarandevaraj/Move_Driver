package com.taximobility.tripCancel

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "CardDetails")
data class CreditCardData(
        var name: String = "",
        @NonNull
        @PrimaryKey
        var id: String = "",
        var type: String = "",
        var month: String = "",
        var year: String = "",
        var card: String = "",
        @Ignore
        var cvv: String = "",
        var default_card: String = "",
        @Ignore
        var original_cardno: String = "",
        @Ignore
        var original_cvv: String = ""
) : Parcelable {

    constructor() : this("", "", "", "", "", "", "", "", "", "")
}