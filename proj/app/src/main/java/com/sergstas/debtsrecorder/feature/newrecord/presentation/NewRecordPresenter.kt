package com.sergstas.debtsrecorder.feature.newrecord.presentation

import android.content.Context
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.feature.newrecord.ui.ntation.NewRecordDao
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class NewRecordPresenter(private val _context: Context, private val _dao: NewRecordDao): MvpPresenter<NewRecordView>() {
    private var _clients: List<Client> = emptyList()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setListeners()
        presenterScope.launch {
            viewState.showLoading(true)
            _clients = _dao.getClients()
            viewState.setClientsSpinner(_clients)
            viewState.showLoading(false)
        }
    }
}