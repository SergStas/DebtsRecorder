package com.sergstas.debtsrecorder.feature.debts.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.feature.debts.data.DebtsDao
import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.debts.adapters.DebtsListAdapter
import com.sergstas.debtsrecorder.feature.debts.presentation.DebtsListView
import com.sergstas.debtsrecorder.feature.debts.presentation.DebtsPresenter
import com.sergstas.debtsrecorder.feature.newrecord.ui.NewRecordActivity
import kotlinx.android.synthetic.main.fragment_debts_list.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class DebtsListFragment(private val _dao: DebtsDao) : MvpAppCompatFragment(R.layout.fragment_debts_list), DebtsListView {
    private val _presenter: DebtsPresenter by moxyPresenter{
        DebtsPresenter(_dao)
    }
    private lateinit var _adapter: DebtsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(debtList_rv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = DebtsListAdapter{v -> _presenter.proceedOnClick(v)}.also { _adapter = it }
        }

        setView()
    }

    private fun setView() {
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
                R.id.debtItem_popup_remove -> _presenter.removeItem(item)
                else -> return@setOnMenuItemClickListener false
            }

            true
        }
    }

    override fun showRemoveConfirmation(): Boolean {
        TODO("Not yet implemented")
    }

    override fun runEditActivity(item: View) {
        TODO("Not yet implemented")
    }
}