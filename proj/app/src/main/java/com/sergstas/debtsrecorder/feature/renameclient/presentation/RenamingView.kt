package com.sergstas.debtsrecorder.feature.renameclient.presentation

import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.feature.renameclient.enums.RenamingValidationError
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface RenamingView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setClient(client: Client)

    @StateStrategyType(SkipStrategy::class)
    fun showValidationError(error: RenamingValidationError)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun close()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(b: Boolean)
}
