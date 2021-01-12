package com.sergstas.debtsrecorder.feature.clients.data

import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.ClientsDebtState

class ClientsDaoImpl(private val _db: DBHolder):
    ClientsDao {
    override fun getClientsState(): List<ClientsDebtState> =
        emptyList()
}