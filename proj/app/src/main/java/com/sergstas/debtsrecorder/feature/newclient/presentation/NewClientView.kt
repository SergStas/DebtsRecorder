package com.sergstas.debtsrecorder.feature.newclient.presentation

import com.sergstas.debtsrecorder.feature.newrecord.enums.ValidationError
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface NewClientView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setListeners()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun close()

    @StateStrategyType(SkipStrategy::class)
    fun showValidationError(error: ValidationError)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(b: Boolean)
}