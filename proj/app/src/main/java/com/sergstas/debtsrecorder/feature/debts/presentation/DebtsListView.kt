package com.sergstas.debtsrecorder.feature.debts.presentation

import android.view.View
import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.debts.enums.DebtsListMessage
import moxy.MvpView
import moxy.viewstate.strategy.*

interface DebtsListView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setList(records: List<Record>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayEmptyListMessage(b: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showLoading(b: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showPopup(item: View)

    @StateStrategyType(SkipStrategy::class)
    fun showRemoveConfirmation()

    @StateStrategyType(SkipStrategy::class)
    fun runEditActivity(item: View)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(message: DebtsListMessage)
}
