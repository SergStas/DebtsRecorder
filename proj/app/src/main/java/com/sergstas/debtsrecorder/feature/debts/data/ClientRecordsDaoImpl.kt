package com.sergstas.debtsrecorder.feature.debts.data

import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record

class ClientRecordsDaoImpl(private val _db: DBHolder, private val _client: Client): RecordsDao {
    override fun getAll(): List<Record> =
        _db.getClientsRecords(_client)

    override fun removeItem(item: Record): Boolean =
        _db.removeRecord(item)
}