package com.sergstas.debtsrecorder.feature.debts.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.debts.adapters.AllRecordsAdapter
import com.sergstas.debtsrecorder.feature.debts.adapters.ClientRecordsAdapter
import com.sergstas.debtsrecorder.feature.debts.adapters.RecordsAdapter
import com.sergstas.debtsrecorder.feature.debts.data.AllRecordsDaoImpl
import com.sergstas.debtsrecorder.feature.debts.data.ClientRecordsDaoImpl
import com.sergstas.debtsrecorder.feature.debts.enums.DebtsActivityType
import com.sergstas.debtsrecorder.feature.debts.enums.DebtsListMessage
import com.sergstas.debtsrecorder.feature.debts.presentation.DebtsListView
import com.sergstas.debtsrecorder.feature.debts.presentation.DebtsPresenter
import com.sergstas.debtsrecorder.feature.debts.ui.dialogs.DebtsConfirmDialogActivity
import com.sergstas.debtsrecorder.feature.edit.ui.EditRecordActivity
import com.sergstas.debtsrecorder.feature.newrecord.ui.NewRecordActivity
import kotlinx.android.synthetic.main.activity_debts.*
import kotlinx.android.synthetic.main.fragment_debt_item.view.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class DebtsActivity : MvpAppCompatActivity(), DebtsListView {
    companion object {
        private const val TYPE_KEY = "TYPE"
        private const val CLIENT_KEY = "CLIENT"

        private const val REMOVE_CONFIRMATION_REQUEST_CODE = 0
        private const val EDIT_ACTIVITY_REQUEST_CODE = 1

        fun getIntent(context: Context, type: DebtsActivityType, client: Client?) =
            Intent(context, DebtsActivity::class.java).apply {
                putExtra(TYPE_KEY, type)
                putExtra(CLIENT_KEY, client)
            }
    }

    private val _presenter: DebtsPresenter by moxyPresenter {
        DebtsPresenter(
            if (intent!!.getSerializableExtra(TYPE_KEY) as DebtsActivityType == DebtsActivityType.ALL_DEBTS)
                AllRecordsDaoImpl(DBHolder(this))
            else
                ClientRecordsDaoImpl(DBHolder(this), intent!!.getParcelableExtra(CLIENT_KEY)!!)
        )
    }

    private lateinit var _adapter: RecordsAdapter
    private var _client: Client? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debts)

        _client = intent!!.getParcelableExtra(CLIENT_KEY)
    }

    override fun setView() {
        with(debtList_rv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = (
                    if (intent!!.getSerializableExtra(TYPE_KEY) as DebtsActivityType == DebtsActivityType.ALL_DEBTS)
                        AllRecordsAdapter{ v -> _presenter.proceedOnClick(v)}
                    else
                        ClientRecordsAdapter{ v -> _presenter.proceedOnClick(v)}
                ).also { _adapter = it }
        }

        debtList_bAdd.setOnClickListener {
            startActivity(
                if (intent!!.getSerializableExtra(TYPE_KEY) == DebtsActivityType.CLIENTS_DEBTS)
                    NewRecordActivity.getIntent(this, _client)
                else
                    Intent(this, NewRecordActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        _presenter.setList()
    }

    override fun setList(records: List<Record>) {
        _adapter.submitList(records)
    }

    override fun displayEmptyListMessage(b: Boolean) {
        debtList_tv_emptyList.isVisible = b
    }

    override fun showLoading(b: Boolean) {
        debtList_pb.isVisible = b
    }

    override fun showPopup(item: View) {
        val menu = PopupMenu(this, item).apply { inflate(R.menu.popup_debt_item) }
        menu.setOnMenuItemClickListener { v: MenuItem? ->
            when(v?.itemId) {
                R.id.debtItem_popup_edit -> _presenter.editItem(extractData(item))
                R.id.debtItem_popup_remove -> _presenter.requireItemRemove(extractData(item))
                else -> return@setOnMenuItemClickListener false
            }

            true
        }
        menu.show()
    }

    override fun showRemoveConfirmation() {
        startActivityForResult(
            Intent(this, DebtsConfirmDialogActivity::class.java),
            REMOVE_CONFIRMATION_REQUEST_CODE)
    }

    override fun runEditActivity(item: Record) {
        startActivityForResult(EditRecordActivity.getIntent(this, item), EDIT_ACTIVITY_REQUEST_CODE)
    }

    override fun showToast(message: DebtsListMessage) {
        val text = when(message) {
            DebtsListMessage.REMOVED_SUCCESSFULLY -> getString(R.string.dlMessage_removeSuccess)
            DebtsListMessage.REMOVING_FAILED -> getString(R.string.dlMessage_removeFail)
            DebtsListMessage.EDITED_SUCCESSFULLY -> getString(R.string.dlMessage_editSuccess)
            else -> getString(R.string.error_unknownError)
        }
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REMOVE_CONFIRMATION_REQUEST_CODE ->
                if (resultCode == Activity.RESULT_OK)
                    _presenter.confirmRemoving()
            EDIT_ACTIVITY_REQUEST_CODE ->
                if (resultCode == Activity.RESULT_OK)
                    _presenter.processEditingResult()
        }
    }

    private fun extractData(item: View) =
        Record(
            sum = item.debtItem_tvSum.text.toString().split(" ")[0].toDouble(),
            clientFirstName = _client?.firstName ?: item.debtItem_tvClient.text.toString().split(' ')[0],
            clientLastName = _client?.lastName ?: item.debtItem_tvClient.text.toString().split(' ')[1],
            doesClientPay = item.debtItem_tvSum.text.toString().contains(getString(R.string.debtItem_GET_noClient)),
            date = item.debtItem_tvDate.text.toString(),

            destDate =
            if (item.debtItem_tvDestDate.text.toString().isEmpty())
                ""
            else
                item.debtItem_tvDestDate.text.toString().split(": ")[1],

            description =
            if (item.debtItem_tvDescription.text.toString().isEmpty())
                ""
            else
                item.debtItem_tvDescription.text.toString().split(": ")[1]
        )
}