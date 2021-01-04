package com.sergstas.debtsrecorder.feature.debts.presentation

import com.sergstas.debtsrecorder.domain.entity.Record
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface DebtsListView: MvpView {
    @StateStrategyType(AddToEndStrategy::class)
    fun setList(records: List<Record>)

    @StateStrategyType(AddToEndStrategy::class)
    fun displayEmptyListMessage()
}
