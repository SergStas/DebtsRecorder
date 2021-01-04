package com.sergstas.debtsrecorder.feature.debts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.domain.entity.Record

class DebtItemFragment : Fragment(R.layout.fragment_debt_item) {
    companion object {
        private const val RECORD_KEY = "RECORD"
        fun newInstance(record: Record) =
            DebtItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RECORD_KEY, record)
                }
            }
    }

    private var _record: Record? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            _record = it.getParcelable(RECORD_KEY)
        }
    }
}