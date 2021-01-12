package com.sergstas.debtsrecorder.domain.entity

data class ClientsDebtState(val client: Client, val totalSum: Double, val debtsCount: Int)