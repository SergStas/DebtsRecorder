package com.sergstas.debtsrecorder.feature.debts.presentation

import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.debts.data.DebtsDao
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class DebtsPresenter(private val _dao: DebtsDao): MvpPresenter<DebtsListView>() {
    private var _recordsList = emptyList<Record>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setList()
    }

    fun setList() {
        presenterScope.launch {
            viewState.showLoading(true)
            _recordsList = _dao.getAll()
            if (_recordsList.isEmpty())
                viewState.displayEmptyListMessage()
            else
                viewState.setList(_recordsList)
            viewState.showLoading(false)
        }
    }
}