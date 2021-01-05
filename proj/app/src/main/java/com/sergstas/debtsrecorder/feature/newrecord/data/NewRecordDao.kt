package com.sergstas.debtsrecorder.feature.newrecord.data

import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record

interface NewRecordDao {
    fun getClients(): List<Client>

    fun addNewRecord(record: Record): Boolean
}
