package com.sergstas.debtsrecorder.feature.clients.presentation

import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.ClientsDebtState
import com.sergstas.debtsrecorder.feature.clients.data.ClientsDao
import com.sergstas.debtsrecorder.feature.clients.enums.ClientsMessage
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class ClientsPresenter(private val _dao: ClientsDao): MvpPresenter<ClientsView>() {
    private var _clients: List<ClientsDebtState> = emptyList()

    fun setList() {
        presenterScope.launch {
            viewState.showLoading(true)
            _clients = _dao.getClientsState()
            viewState.showEmptyListMessage(_clients.isEmpty())
            viewState.showClientsInfo(_clients)
            viewState.showLoading(false)
        }
    }

    fun cleanupClientsHistory(name: String) {
        presenterScope.launch {
            viewState.showLoading(true)
            viewState.showMessage(
                 if (_dao.cleanupRecords(Client.fromString(name)!!))
                     ClientsMessage.HISTORY_CLEANED
                 else
                     ClientsMessage.UNKNOWN_ERROR
            )
            viewState.showLoading(false)
        }
        setList()
    }

    fun removeClient(name: String) {
        presenterScope.launch {
            viewState.showLoading(true)
            viewState.showMessage(
                if (_dao.removeClient(Client.fromString(name)!!))
                    ClientsMessage.CLIENT_REMOVED
                else
                    ClientsMessage.UNKNOWN_ERROR
            )
            viewState.showLoading(false)
        }
        setList()
    }
}