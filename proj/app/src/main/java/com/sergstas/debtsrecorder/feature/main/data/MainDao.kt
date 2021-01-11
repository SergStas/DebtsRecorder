package com.sergstas.debtsrecorder.feature.main.data

interface MainDao {
    fun getDebtorsInfo(): Pair<Int, Int>
}

