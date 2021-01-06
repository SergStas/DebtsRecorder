package com.sergstas.debtsrecorder.feature.newclient.data

import com.sergstas.debtsrecorder.domain.entity.Client

interface NewClientDao {
    fun doesClientExist(client: Client): Boolean

    fun addClient(client: Client): Boolean
}