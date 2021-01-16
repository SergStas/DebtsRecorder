package com.sergstas.debtsrecorder.feature.clients.ui

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.ClientsDebtState
import com.sergstas.debtsrecorder.feature.clients.adapters.ClientsAdapter
import com.sergstas.debtsrecorder.feature.clients.data.ClientsDaoImpl
import com.sergstas.debtsrecorder.feature.clients.presentation.ClientsPresenter
import com.sergstas.debtsrecorder.feature.clients.presentation.ClientsView
import com.sergstas.debtsrecorder.feature.clientrecords.ui.ClientRecordsActivity
import kotlinx.android.synthetic.main.activity_clients.*
import kotlinx.android.synthetic.main.fragment_clients_item.view.*
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
            adapter = ClientsAdapter{v -> showPopup(v)}.also{ _adapter = it }
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

    private fun showPopup(v: View) {
        val menu = PopupMenu(this, v)
            .apply { inflate(R.menu.popup_client_item) }

        menu.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.clientItem_popup_records -> goToClientRecords(v.clientItem_tvName.text.toString())
                R.id.clientItem_popup_rename -> _presenter.renameClient(v.clientItem_tvName.text.toString())
                R.id.clientItem_popup_cleanup -> _presenter.cleanupClientsHistory(v.clientItem_tvName.text.toString())
                R.id.clientItem_popup_remove -> _presenter.removeClient(v.clientItem_tvName.text.toString())
                else -> return@setOnMenuItemClickListener false
            }
            true
        }

        menu.show()
    }

    private fun goToClientRecords(name: String) {
        startActivity(ClientRecordsActivity.getIntent(this, Client(
            name.split(" ")[0],
            name.split(" ")[1]
        )))
    }
}