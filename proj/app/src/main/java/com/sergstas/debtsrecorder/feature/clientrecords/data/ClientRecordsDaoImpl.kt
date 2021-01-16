package com.sergstas.debtsrecorder.feature.clientrecords.data

import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record

class ClientRecordsDaoImpl(private val _db: DBHolder): ClientRecordsDao {
    override fun getRecords(client: Client): List<Record> =
        _db.getClientsRecords(client)
}