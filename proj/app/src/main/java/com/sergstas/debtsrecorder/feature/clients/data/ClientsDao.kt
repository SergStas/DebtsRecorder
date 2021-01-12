package com.sergstas.debtsrecorder.feature.clients.data

import com.sergstas.debtsrecorder.domain.entity.ClientsDebtState

interface ClientsDao {
    fun getClientsState(): List<ClientsDebtState>
}

