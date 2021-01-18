package com.sergstas.debtsrecorder.feature.clients.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
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
import com.sergstas.debtsrecorder.feature.clients.enums.ClientsMessage
import com.sergstas.debtsrecorder.feature.clients.ui.dialogs.ClientsConfirmDialogActivity
import com.sergstas.debtsrecorder.feature.newclient.ui.NewClientActivity
import com.sergstas.debtsrecorder.feature.renameclient.ui.RenamingActivity
import kotlinx.android.synthetic.main.activity_clients.*
import kotlinx.android.synthetic.main.fragment_clients_item.*
import kotlinx.android.synthetic.main.fragment_clients_item.view.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class ClientsActivity : MvpAppCompatActivity(), ClientsView {
    companion object {
        private const val RENAME_CLIENT_REQUEST_CODE = 0
        private const val CONFIRM_CLEANUP_REQUEST_CODE = 1
        private const val CONFIRM_REMOVE_REQUEST_CODE = 2
        private const val NEW_CLIENT_REQUEST_CODE = 3
    }

    private lateinit var _adapter: ClientsAdapter

    private val _presenter: ClientsPresenter by moxyPresenter{
        ClientsPresenter(ClientsDaoImpl(DBHolder(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients)
        setView()
    }

    override fun onResume() {
        super.onResume()
        _presenter.setList()
    }

    private fun setView() {
        clients_bNewClient.setOnClickListener {
            startActivityForResult(
                Intent(this, NewClientActivity::class.java),
                NEW_CLIENT_REQUEST_CODE
            )
        }

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

    override fun showMessage(message: ClientsMessage) {
        val text = getString(when (message) {
            ClientsMessage.CLIENT_RENAMED -> R.string.clMessage_clientRenamed
            ClientsMessage.CLIENT_REMOVED -> R.string.clMessage_clientRemoved
            ClientsMessage.HISTORY_CLEANED -> R.string.clMessage_cleanup
            ClientsMessage.CLIENT_CREATED -> R.string.clMessage_clientCreated
            ClientsMessage.UNKNOWN_ERROR -> R.string.clMessage_unknownError
        })

        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    private fun showPopup(v: View) {
        val menu = PopupMenu(this, v)
            .apply { inflate(R.menu.popup_client_item) }

        menu.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.clientItem_popup_records -> goToClientRecords(v.clientItem_tvName.text.toString())
                R.id.clientItem_popup_rename -> showRenamingDialog(v.clientItem_tvName.text.toString())
                R.id.clientItem_popup_cleanup -> showCleanupConfirmationDialog(v.clientItem_tvName.text.toString())
                R.id.clientItem_popup_remove -> showRemoveConfirmationDialog(v.clientItem_tvName.text.toString())
                else -> return@setOnMenuItemClickListener false
            }
            true
        }

        menu.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                RENAME_CLIENT_REQUEST_CODE -> showMessage(ClientsMessage.CLIENT_RENAMED)
                CONFIRM_CLEANUP_REQUEST_CODE -> _presenter.cleanupClientsHistory(data!!.getStringExtra(ClientsConfirmDialogActivity.CLIENT_ARG_KEY)!!)
                CONFIRM_REMOVE_REQUEST_CODE -> _presenter.removeClient(data!!.getStringExtra(ClientsConfirmDialogActivity.CLIENT_ARG_KEY)!!)
                NEW_CLIENT_REQUEST_CODE -> showMessage(ClientsMessage.CLIENT_CREATED)
            }
    }

    private fun showRenamingDialog(oldName: String) {
        startActivityForResult(RenamingActivity.getIntent(this, oldName), RENAME_CLIENT_REQUEST_CODE)
    }

    private fun showCleanupConfirmationDialog(name: String) {
        startActivityForResult(
            ClientsConfirmDialogActivity.getIntent(this, name, getString(R.string.confirmCI_cleanup_content)),
            CONFIRM_CLEANUP_REQUEST_CODE
        )
    }

    private fun showRemoveConfirmationDialog(name: String) {
        startActivityForResult(
            ClientsConfirmDialogActivity.getIntent(this, name, getString(R.string.confirmCI_remove_content)),
            CONFIRM_REMOVE_REQUEST_CODE
        )
    }

    private fun goToClientRecords(name: String) {
        startActivity(ClientRecordsActivity.getIntent(this, Client(
            name.split(" ")[0],
            name.split(" ")[1]
        )))
    }
}