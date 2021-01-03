package com.sergstas.debtsrecorder.data

import com.sergstas.debtsrecorder.domain.entity.DebtRecord

interface DebtsDao {
    fun getAll(): List<DebtRecord>
}