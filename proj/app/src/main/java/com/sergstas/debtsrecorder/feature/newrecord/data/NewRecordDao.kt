package com.sergstas.debtsrecorder.feature.newrecord.ui.ntation

import com.sergstas.debtsrecorder.domain.entity.Client

interface NewRecordDao {
    fun getClients(): List<Client>

    fun addNewRecord(): Boolean
}
