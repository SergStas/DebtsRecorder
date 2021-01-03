package com.sergstas.debtsrecorder.feature.debts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.DebtsDao
import com.sergstas.debtsrecorder.domain.entity.DebtRecord
import com.sergstas.debtsrecorder.feature.debts.adapters.DebtsListAdapter
import com.sergstas.debtsrecorder.feature.debts.presentation.DebtsListView
import com.sergstas.debtsrecorder.feature.debts.presentation.DebtsPresenter
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
            adapter = DebtsListAdapter().also { _adapter = it }
        }
    }

    override fun setList(records: List<DebtRecord>) {
        _adapter.submitList(records)
    }
}