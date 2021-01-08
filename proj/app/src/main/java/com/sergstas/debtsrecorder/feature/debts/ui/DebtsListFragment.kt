package com.sergstas.debtsrecorder.feature.debts.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.feature.debts.data.DebtsDao
import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.debts.adapters.DebtsListAdapter
import com.sergstas.debtsrecorder.feature.debts.enums.DebtsListMessage
import com.sergstas.debtsrecorder.feature.debts.presentation.DebtsListView
import com.sergstas.debtsrecorder.feature.debts.presentation.DebtsPresenter
import com.sergstas.debtsrecorder.feature.newrecord.ui.NewRecordActivity
import kotlinx.android.synthetic.main.fragment_debt_item.*
import kotlinx.android.synthetic.main.fragment_debt_item.view.*
import kotlinx.android.synthetic.main.fragment_debts_list.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class DebtsListFragment(private val _dao: DebtsDao) : MvpAppCompatFragment(R.layout.fragment_debts_list), DebtsListView {
    companion object {
        private const val REMOVE_CONFIRMATION_REQUEST_CODE = 0
    }

    private val _presenter: DebtsPresenter by moxyPresenter{
        DebtsPresenter(_dao)
    }
    private lateinit var _adapter: DebtsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() {
        with(debtList_rv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = DebtsListAdapter{v -> _presenter.proceedOnClick(v)}.also { _adapter = it }
        }

        debtList_bAdd.setOnClickListener {
            startActivity(Intent(context, NewRecordActivity::class.java))
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
        val menu = PopupMenu(context, item).apply { inflate(R.menu.popup_debt_item) }
        menu.setOnMenuItemClickListener { v: MenuItem? ->
            when(v?.itemId) {
                R.id.debtItem_popup_edit -> _presenter.editItem(item)
                R.id.debtItem_popup_remove -> _presenter.requireItemRemove(
                    Record(
                        sum = item.debtItem_tvSum.text.toString().split(" rub")[0].toDouble(),
                        clientFirstName = item.debtItem_tvClient.text.toString().split(' ')[0],
                        clientLastName = item.debtItem_tvClient.text.toString().split(' ')[1],
                        doesClientPay = item.debtItem_tvSum.text.toString().split(" - ")[1] == getString(R.string.debtItem_GET),
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
                )
                else -> return@setOnMenuItemClickListener false
            }

            true
        }
        menu.show()
    }

    override fun showRemoveConfirmation() {
        startActivityForResult(Intent(context, ConfirmationRemoveActivity::class.java),
            REMOVE_CONFIRMATION_REQUEST_CODE)
    }

    override fun runEditActivity(item: View) {

    }

    override fun showToast(message: DebtsListMessage) {
        val text = when(message) {
            DebtsListMessage.RemovedSuccessfully -> getString(R.string.dlMessage_removeSuccess)
            DebtsListMessage.RemovingFailed -> getString(R.string.dlMessage_removeFail)
            else -> getString(R.string.error_unknownError)
        }
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REMOVE_CONFIRMATION_REQUEST_CODE ->
                if (resultCode == RESULT_OK)
                    _presenter.confirmRemoving()
        }
    }
}