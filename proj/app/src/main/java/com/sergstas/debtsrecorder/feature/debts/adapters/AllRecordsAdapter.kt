package com.sergstas.debtsrecorder.feature.debts.adapters

import android.view.View
import androidx.core.view.isVisible
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.domain.entity.Record
import kotlinx.android.synthetic.main.fragment_debt_item.*

class AllRecordsAdapter(private val _onClick: (View) -> Unit): RecordsAdapter() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        setItemView(getItem(position), holder)
    }

    private fun setItemView(record: Record, holder: ViewHolder) {
        with (holder.containerView.context) {
            holder.debtItem_tvSum.text = String.format(getString(R.string.debtItem_tvSum_ph),
                record.sum,
                getString(if (record.doesClientPay) R.string.debtItem_GET else R.string.debtItem_PAY))
            holder.debtItem_tvClient.text = record.clientString
            holder.debtItem_tvDate.text = record.date

            holder.containerView.setOnClickListener {
                _onClick(holder.containerView)
            }

            holder.debtItem_tvDestDate.isVisible = !record.destDate.isNullOrEmpty()
            holder.debtItem_tvDestDate.text =
                if (!record.destDate.isNullOrEmpty())
                    String.format(getString(R.string.debtItem_tvDestDate_ph), record.destDate)
                else ""

            holder.debtItem_tvDescription.isVisible = !record.description.isNullOrEmpty()
            holder.debtItem_tvDescription.text =
                if (!record.description.isNullOrEmpty())
                    String.format(getString(R.string.debtItem_tvDescription_ph), record.description)
                else ""
        }
    }
}