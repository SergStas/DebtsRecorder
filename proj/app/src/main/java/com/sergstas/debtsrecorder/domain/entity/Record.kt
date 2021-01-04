package com.sergstas.debtsrecorder.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Record(
    val id: Int,
    val sum: Double,
    val clientFirstName: String,
    val clientLastName: String,
    val doesClientPay: Boolean,
    val date: String,
    val destDate: String?,
    val description: String?
): Parcelable
