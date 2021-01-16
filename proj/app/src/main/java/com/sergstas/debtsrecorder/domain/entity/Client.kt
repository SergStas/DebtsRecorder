package com.sergstas.debtsrecorder.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Client(val firstName: String, val lastName: String): Parcelable {
    val fullNameString: String
    get() = "$firstName $lastName"
}