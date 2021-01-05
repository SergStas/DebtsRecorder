package com.sergstas.debtsrecorder.feature.newrecord.data

import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record

class NewRecordDaoImpl(private val _db: DBHolder): NewRecordDao {
    override fun getClients(): List<Client> {
        return emptyList()
    }

    override fun addNewRecord(record: Record): Boolean {
        return false
    }
}