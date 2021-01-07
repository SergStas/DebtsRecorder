package com.sergstas.debtsrecorder.feature.debts.presentation

import com.sergstas.debtsrecorder.domain.entity.Record
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface DebtsListView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setList(records: List<Record>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayEmptyListMessage(b: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showLoading(b: Boolean)
}
