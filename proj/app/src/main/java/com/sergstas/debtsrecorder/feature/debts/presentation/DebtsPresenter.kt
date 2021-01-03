package com.sergstas.debtsrecorder.feature.debts.presentation

import com.sergstas.debtsrecorder.data.DebtsDao
import moxy.MvpPresenter

class DebtsPresenter(private val _dao: DebtsDao): MvpPresenter<DebtsListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setList(_dao.getAll())
    }
}