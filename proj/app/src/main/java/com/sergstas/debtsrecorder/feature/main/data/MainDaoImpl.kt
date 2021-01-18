package com.sergstas.debtsrecorder.feature.main.data

import com.sergstas.debtsrecorder.data.db.DBHolder

class MainDaoImpl(private val _db: DBHolder): MainDao {
    override fun getDebtorsInfo(): Pair<Int, Int> =
        Pair(
            _db.getClientsInfo().filter {
                p -> p.value.sumByDouble {
                    r ->
                if (r.doesClientPay)
                    r.sum
                else
                    -1 * r.sum
                } < 0
            }.count(),

            _db.getClientsInfo().filter {
                p -> p.value.sumByDouble {
                    r ->
                if (r.doesClientPay)
                    r.sum
                else
                    -1 * r.sum
                } > 0
            }.count()
        )
}