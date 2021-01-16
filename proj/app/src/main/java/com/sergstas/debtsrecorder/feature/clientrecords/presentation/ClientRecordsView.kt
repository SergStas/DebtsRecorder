package com.sergstas.debtsrecorder.feature.clientrecords.presentation

import com.sergstas.debtsrecorder.domain.entity.Record
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ClientRecordsView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setList(list: List<Record>)

    @StateStrategyType(SkipStrategy::class)
    fun showLoading(b: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setView()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEmptyListMessage(b: Boolean)
}