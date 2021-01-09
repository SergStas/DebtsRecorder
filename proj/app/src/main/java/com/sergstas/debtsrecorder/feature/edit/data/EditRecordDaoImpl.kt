package com.sergstas.debtsrecorder.feature.edit.data

import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record

class EditRecordDaoImpl(private val _db: DBHolder): EditRecordDao {
    override fun update(old: Record, new: Record): Boolean =
        _db.updateRecord(old, new)

    override fun getClients(): List<Client> =
        _db.getClients()
}