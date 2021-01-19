package com.sergstas.debtsrecorder.feature.debts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.domain.entity.Record
import kotlinx.android.extensions.LayoutContainer

open class RecordsAdapter: ListAdapter<Record, RecordsAdapter.ViewHolder>(
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer
}