package com.sergstas.debtsrecorder.feature.clientrecords.data

import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record

interface ClientRecordsDao {
    fun getRecords(client: Client): List<Record>
}

