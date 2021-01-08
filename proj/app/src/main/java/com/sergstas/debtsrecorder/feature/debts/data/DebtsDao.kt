package com.sergstas.debtsrecorder.feature.debts.data

import com.sergstas.debtsrecorder.domain.entity.Record

interface DebtsDao {
    fun getAll(): List<Record>
    fun removeItem(
        sum: String,
        clientPays: String,
        client: String,
        date: String,
        destDate: String,
        description: String
    )
}