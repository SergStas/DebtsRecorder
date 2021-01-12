package com.sergstas.debtsrecorder.feature.clients.ui

import android.os.Bundle
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.domain.entity.ClientsDebtState
import com.sergstas.debtsrecorder.feature.clients.presentation.ClientsView
import moxy.MvpAppCompatActivity

class ClientsActivity : MvpAppCompatActivity(), ClientsView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients)
    }

    override fun showLoading(b: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showClientsInfo(list: List<ClientsDebtState>) {
        TODO("Not yet implemented")
    }

    override fun showEmptyListMessage(b: Boolean) {
        TODO("Not yet implemented")
    }
}