package com.sergstas.debtsrecorder.feature.main.presntation

import com.sergstas.debtsrecorder.feature.main.enums.MainMessage
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setListeners()

    @StateStrategyType(SkipStrategy::class)
    fun showLoading(b: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setDebtorsInfo(to: Int, from: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showMessage(message: MainMessage)
}