package com.sergstas.debtsrecorder.feature.edit.data

import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record

interface EditRecordDao {
    fun update(old: Record, new: Record): Boolean
    fun getClients(): List<Client>
}

