package com.sergstas.debtsrecorder.feature.main.data

import com.sergstas.debtsrecorder.data.db.DBHolder

class MainDaoImpl(private val _db: DBHolder):
    MainDao {
    override fun getDebtorsInfo(): Pair<Int, Int> {
        return Pair(0, 0)
    }
}