package com.sergstas.debtsrecorder.feature.newclient.data

import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client

class NewClientDaoImpl(private val _db: DBHolder): NewClientDao {
    override fun doesClientExist(client: Client): Boolean =
        _db.getClientsId(client) != null

    override fun addClient(client: Client): Boolean =
        _db.addClient(client)
}

