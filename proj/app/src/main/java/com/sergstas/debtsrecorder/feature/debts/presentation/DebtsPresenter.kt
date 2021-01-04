package com.sergstas.debtsrecorder.feature.debts.presentation

import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.debts.data.DebtsDao
import moxy.MvpPresenter

class DebtsPresenter(private val _dao: DebtsDao): MvpPresenter<DebtsListView>() {
    private var _recordsList = emptyList<Record>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setList()
    }

    private fun setList() {
        _recordsList = _dao.getAll()
        if (_recordsList.isEmpty())
            viewState.displayEmptyListMessage()
        else
            viewState.setList(_recordsList)
    }
}