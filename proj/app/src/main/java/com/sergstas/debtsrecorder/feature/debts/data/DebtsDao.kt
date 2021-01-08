package com.sergstas.debtsrecorder.feature.debts.data

import com.sergstas.debtsrecorder.domain.entity.Record

interface DebtsDao {
    fun getAll(): List<Record>
    fun removeItem(item: Record): Boolean
}