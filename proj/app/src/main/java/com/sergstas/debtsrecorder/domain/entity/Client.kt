package com.sergstas.debtsrecorder.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.lang.Exception

@Parcelize
data class Client(val firstName: String, val lastName: String): Parcelable {
    companion object {
        fun fromString(name: String): Client? =
            try {
                if (name.split(" ").count() == 2)
                    Client(
                        name.split(" ")[0],
                        name.split(" ")[1]
                    )
                else null
            }
            catch (e: Exception) {null}
    }

    val fullNameString: String
    get() = "$firstName $lastName"
}