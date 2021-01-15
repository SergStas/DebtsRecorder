package com.sergstas.debtsrecorder.feature.clients.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.ClientsDebtState
import com.sergstas.debtsrecorder.feature.clients.adapters.ClientsAdapter
import com.sergstas.debtsrecorder.feature.clients.data.ClientsDao
import com.sergstas.debtsrecorder.feature.clients.data.ClientsDaoImpl
import com.sergstas.debtsrecorder.feature.clients.presentation.ClientsPresenter
import com.sergstas.debtsrecorder.feature.clients.presentation.ClientsView
import com.sergstas.debtsrecorder.feature.debts.adapters.DebtsListAdapter
import com.sergstas.debtsrecorder.feature.debts.presentation.DebtsPresenter
import com.sergstas.debtsrecorder.feature.newrecord.ui.NewRecordActivity
import kotlinx.android.synthetic.main.activity_clients.*
import kotlinx.android.synthetic.main.fragment_debts_list.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class ClientsActivity : MvpAppCompatActivity(), ClientsView {
    private lateinit var _adapter: ClientsAdapter

    private val _presenter: ClientsPresenter by moxyPresenter{
        ClientsPresenter(ClientsDaoImpl(DBHolder(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients)
        setView()
    }

    private fun setView() {
        with(clients_rv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ClientsAdapter().also{ _adapter = it }
        }
    }

    override fun showLoading(b: Boolean) {
        clients_pb.isVisible = b
    }

    override fun showClientsInfo(list: List<ClientsDebtState>) {
        _adapter.submitList(list)
    }

    override fun showEmptyListMessage(b: Boolean) {
        clients_tvEmptyList.isVisible = b
    }
}