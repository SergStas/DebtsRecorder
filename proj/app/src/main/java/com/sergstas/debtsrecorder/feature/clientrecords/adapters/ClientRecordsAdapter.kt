package com.sergstas.debtsrecorder.feature.clientrecords.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.domain.entity.Record
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_debt_item.*

class ClientRecordsAdapter: ListAdapter<Record, ClientRecordsAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_debt_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setItemView(getItem(position), holder)
    }

    private fun setItemView(record: Record, holder: ViewHolder) {
        with(holder.containerView.context) {
            holder.debtItem_tvSum.text = String.format(getString(R.string.debtItem_tvSum_ph),
                record.sum,
                getString(if (record.doesClientPay) R.string.debtItem_GET_noClient else R.string.debtItem_PAY_noClient))
            holder.debtItem_tvClient.isVisible = false
            holder.debtItem_tvDate.text = record.date

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

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer
}