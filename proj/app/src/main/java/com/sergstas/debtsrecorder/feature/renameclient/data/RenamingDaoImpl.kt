package com.sergstas.debtsrecorder.feature.renameclient.data

import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client

class RenamingDaoImpl(private val _db: DBHolder): RenamingDao {
    override fun checkIsNameOccupied(client: Client): Boolean =
        _db.getClientsId(client) != null

    override fun renameClient(old: Client, new: Client): Boolean =
        _db.renameClient(old, new)
}
