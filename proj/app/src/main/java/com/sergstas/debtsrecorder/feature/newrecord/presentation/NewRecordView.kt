package com.sergstas.debtsrecorder.feature.newrecord.presentation

import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.feature.newrecord.enums.ValidationError
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

interface NewRecordView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setListeners()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setClientsSpinner(clients: List<Client>)

    @StateStrategyType(SkipStrategy::class)
    fun showLoading(b: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showValidationError(error: ValidationError)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEmptyDescriptionWarning()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun close()
}