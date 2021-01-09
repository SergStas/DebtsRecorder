package com.sergstas.debtsrecorder.feature.edit.presentation

import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.feature.newrecord.enums.ValidationError
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface EditRecordView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setView()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setClientsSpinner(list: List<Client>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(b: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showValidationError(error: ValidationError)

    @StateStrategyType(SkipStrategy::class)
    fun close()

    @StateStrategyType(SkipStrategy::class)
    fun showClientAddedMessage()

    @StateStrategyType(SkipStrategy::class)
    fun showEmptyDescriptionWarning()
}