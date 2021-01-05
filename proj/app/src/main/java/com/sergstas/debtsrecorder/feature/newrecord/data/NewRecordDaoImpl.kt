package com.sergstas.debtsrecorder.feature.newrecord.data

import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.feature.newrecord.ui.ntation.NewRecordDao

class NewRecordDaoImpl(private val _db: DBHolder): NewRecordDao {
    override fun getClients(): List<Client> {
        return emptyList()
    }

    override fun addNewRecord(): Boolean {
        return false
    }
}