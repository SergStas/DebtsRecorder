package com.sergstas.debtsrecorder.feature.clients.presentation

import com.sergstas.debtsrecorder.domain.entity.ClientsDebtState
import com.sergstas.debtsrecorder.feature.clients.data.ClientsDao
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class ClientsPresenter(private val _dao: ClientsDao): MvpPresenter<ClientsView>() {
    private var _clients: List<ClientsDebtState> = emptyList()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        setList()
    }

    private fun setList() {
        presenterScope.launch {
            viewState.showLoading(true)
            _clients = _dao.getClientsState()
            viewState.showEmptyListMessage(_clients.isEmpty())
            viewState.showClientsInfo(_clients)
            viewState.showLoading(false)
        }
    }
}