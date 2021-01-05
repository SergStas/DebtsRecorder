package com.sergstas.debtsrecorder.feature.newrecord.presentation

import com.sergstas.debtsrecorder.domain.entity.Client
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface NewRecordView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setListeners()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setClientsSpinner(clients: List<Client>)

    @StateStrategyType(SkipStrategy::class)
    fun showLoading(b: Boolean)
}