package com.sergstas.debtsrecorder.feature.clientrecords.presentation

import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.clientrecords.data.ClientRecordsDao
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class ClientRecordsPresenter(private val _dao: ClientRecordsDao): MvpPresenter<ClientRecordsView>() {
    private var _list: List<Record> = emptyList()
    private lateinit var _client: Client

    fun passClientInfo(client: Client) {
        _client = client
        viewState.setView()
        setList()
    }

    private fun setList() {
        presenterScope.launch {
            viewState.showLoading(true)
            _list = _dao.getRecords(_client)
            viewState.showEmptyListMessage(_list.isEmpty())
            viewState.setList(_list)
            viewState.showLoading(false)
        }
    }
}

