package com.sergstas.debtsrecorder.feature.clients.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.domain.entity.ClientsDebtState
import com.sergstas.debtsrecorder.domain.entity.Record
import kotlinx.android.extensions.LayoutContainer

class ClientsAdapter: ListAdapter<ClientsDebtState, ClientsAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<ClientsDebtState>() {
        override fun areItemsTheSame(oldItem: ClientsDebtState, newItem: ClientsDebtState): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ClientsDebtState, newItem: ClientsDebtState): Boolean =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_clients_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setItemView(getItem(position), holder)
    }

    private fun setItemView(сдшуте: ClientsDebtState, holder: ViewHolder) {

    }

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer
}