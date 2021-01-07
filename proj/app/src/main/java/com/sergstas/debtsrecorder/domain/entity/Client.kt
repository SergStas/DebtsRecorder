package com.sergstas.debtsrecorder.domain.entity

data class Client(val firstName: String, val lastName: String) {
    val fullNameString: String
    get() = "$firstName $lastName"
}