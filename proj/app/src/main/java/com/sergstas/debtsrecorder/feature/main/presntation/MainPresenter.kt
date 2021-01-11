package com.sergstas.debtsrecorder.feature.main.presntation

import com.sergstas.debtsrecorder.feature.main.data.MainDao
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class MainPresenter(private val _dao: MainDao): MvpPresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setListeners()
        updateDebtorsInfo()
    }

    fun updateDebtorsInfo() {
        presenterScope.launch {
            viewState.showLoading(true)
            val debtorsInfo = _dao.getDebtorsInfo()
            viewState.setDebtorsInfo(debtorsInfo.first, debtorsInfo.second)
            viewState.showLoading(false)
        }
    }
}