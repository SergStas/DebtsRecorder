package com.sergstas.debtsrecorder.feature.newclient.data

import com.sergstas.debtsrecorder.domain.entity.Client

class NewClientDaoImpl: NewClientDao {
    override fun doesClientExist(client: Client): Boolean {
        return false
    }

    override fun addClient(client: Client): Boolean {
        return false
    }
}

