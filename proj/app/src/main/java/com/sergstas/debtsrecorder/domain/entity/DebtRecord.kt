package com.sergstas.debtsrecorder.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DebtRecord(val amount: Double, val date: String, val description: String?): Parcelable
