package com.sergstas.debtsrecorder.feature.debts.presentation

import android.view.View
import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.debts.data.DebtsDao
import kotlinx.android.synthetic.main.fragment_debt_item.view.*
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
                viewState.displayEmptyListMessage(true)
            else {
                viewState.displayEmptyListMessage(false)
                viewState.setList(_recordsList.reversed())
            }
            viewState.showLoading(false)
        }
    }

    fun proceedOnClick(item: View) {
        viewState.showPopup(item)
    }

    fun removeItem(item: View) {
        if (!viewState.showRemoveConfirmation()) return
        presenterScope.launch {
            viewState.showLoading(true)
            _dao.removeItem(
                item.debtItem_tvSum.text.split(" - ")[0],
                item.debtItem_tvSum.text.split(" - ")[1],
                item.debtItem_tvClient.text.toString(),
                item.debtItem_tvDate.text.toString(),
                item.debtItem_tvDestDate.text.toString(),
                item.debtItem_tvDescription.text.toString()
            )
            setList()
            viewState.showLoading(false)
        }
    }

    fun editItem(item: View) {
        viewState.runEditActivity(item)
    }
}