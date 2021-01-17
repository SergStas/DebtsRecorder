package com.sergstas.debtsrecorder.feature.clients.data

import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.ClientsDebtState

class ClientsDaoImpl(private val _db: DBHolder): ClientsDao {
    override fun getClientsState(): List<ClientsDebtState> =
        _db.getClientsInfo().map { e -> ClientsDebtState(
            e.key,
            e.value.sumByDouble { r -> if (r.doesClientPay) r.sum else -1 * r.sum },
            e.value.count()
        ) }

    override fun cleanupRecords(client: Client): Boolean =
        _db.cleanupRecords(client)

    override fun removeClient(client: Client): Boolean =
        _db.removeClient(client)
}