package com.sergstas.debtsrecorder.feature.clients.presentation

import com.sergstas.debtsrecorder.domain.entity.ClientsDebtState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ClientsView: MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showLoading(b: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showClientsInfo(list: List<ClientsDebtState>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEmptyListMessage(b: Boolean)
}
