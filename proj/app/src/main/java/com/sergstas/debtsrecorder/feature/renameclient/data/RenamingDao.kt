package com.sergstas.debtsrecorder.feature.renameclient.data

import com.sergstas.debtsrecorder.domain.entity.Client

interface RenamingDao {
    fun checkIsNameOccupied(client: Client): Boolean

    fun renameClient(old: Client, new: Client): Boolean
}
