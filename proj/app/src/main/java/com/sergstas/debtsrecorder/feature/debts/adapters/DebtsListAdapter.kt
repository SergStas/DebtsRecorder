package com.sergstas.debtsrecorder.feature.debts.adapters

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

class DebtsListAdapter(
    private val onClick: (View) -> Unit
): ListAdapter<Record, DebtsListAdapter.ViewHolder>(
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
        with (holder.containerView.context) {
            holder.debtItem_tvSum.text = String.format(getString(R.string.debtItem_tvSum_ph),
            record.sum,
            getString(if (record.doesClientPay) R.string.debtItem_GET else R.string.debtItem_PAY))
            holder.debtItem_tvClient.text = record.clientString
            holder.debtItem_tvDate.text = record.date

            holder.containerView.setOnClickListener { onClick }

            if (!record.destDate.isNullOrEmpty())
                holder.debtItem_tvDestDate.text =
                    String.format(getString(R.string.debtItem_tvDestDate_ph), record.destDate)
            else
                holder.debtItem_tvDestDate.isVisible = false

            if (!record.description.isNullOrEmpty())
                holder.debtItem_tvDescription.text =
                    String.format(getString(R.string.debtItem_tvDescription_ph), record.description)
            else
                holder.debtItem_tvDescription.isVisible = false
        }
    }

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer
}