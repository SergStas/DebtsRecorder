package com.sergstas.debtsrecorder.data

import com.sergstas.debtsrecorder.domain.entity.DebtRecord

class DebtsDaoImpl: DebtsDao {
    override fun getAll(): List<DebtRecord> {
        return listOf<DebtRecord>(
            DebtRecord(0.0,"Не ну хули", null),
            DebtRecord(0.0,"hello", null),
            DebtRecord(0.0, "world", null),
            DebtRecord(0.0, "епта", null)
        )
    }
}