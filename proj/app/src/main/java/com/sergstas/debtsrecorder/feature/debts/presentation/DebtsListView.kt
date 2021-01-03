package com.sergstas.debtsrecorder.feature.debts.presentation

import com.sergstas.debtsrecorder.domain.entity.DebtRecord
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

interface DebtsListView: MvpView {
    @StateStrategyType(AddToEndStrategy::class)
    fun setList(records: List<DebtRecord>)
}
