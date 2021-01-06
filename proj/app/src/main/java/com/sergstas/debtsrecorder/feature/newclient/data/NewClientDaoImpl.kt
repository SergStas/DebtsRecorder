package com.sergstas.debtsrecorder.feature.newclient.data

import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client

class NewClientDaoImpl(_db: DBHolder): NewClientDao {
    override fun doesClientExist(client: Client): Boolean {
        return false
    }

    override fun addClient(client: Client): Boolean {
        return false
    }
}

