package com.sergstas.debtsrecorder.feature.debts.presentation

import android.view.View
import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.debts.data.DebtsDao
import com.sergstas.debtsrecorder.feature.debts.enums.DebtsListMessage
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class DebtsPresenter(private val _dao: DebtsDao): MvpPresenter<DebtsListView>() {
    private var _recordsList = emptyList<Record>()

    private var _itemToRemove: Record? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setList()
    }

    fun setList() {
        presenterScope.launch {
            viewState.showLoading(true)
            _recordsList = _dao.getAll()
            viewState.setList(_recordsList.reversed())
            viewState.displayEmptyListMessage(_recordsList.isEmpty())
            viewState.showLoading(false)
        }
    }

    fun proceedOnClick(item: View) {
        viewState.showPopup(item)
    }

    fun requireItemRemove(item: Record) {
        _itemToRemove = item
        viewState.showRemoveConfirmation()
    }

    fun editItem(item: View) {
        viewState.runEditActivity(item)
    }

    fun confirmRemoving() {
        presenterScope.launch {
            viewState.showLoading(true)
            viewState.showToast(
                if (_dao.removeItem(_itemToRemove!!))
                    DebtsListMessage.REMOVED_SUCCESSFULLY
                else
                    DebtsListMessage.REMOVING_FAILED)
            viewState.showLoading(false)
        }
        setList()
    }

    fun processEditingResult() {
        viewState.showToast(DebtsListMessage.EDITED_SUCCESSFULLY)
        setList()
    }
}
