package com.sergstas.debtsrecorder.feature.debts.data

import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.feature.debts.data.DebtsDao
import com.sergstas.debtsrecorder.domain.entity.Record

class DebtsDaoImpl(private val _db: DBHolder): DebtsDao {

    override fun getAll(): List<Record> = _db.getAllDebtsRecords()

    override fun removeItem(item: Record): Boolean =
        _db.removeRecord(item)
}