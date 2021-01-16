package com.sergstas.debtsrecorder.feature.clients.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.domain.entity.ClientsDebtState
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_clients_item.*
import kotlin.math.abs
import kotlin.math.roundToInt

class ClientsAdapter(private val onClick: (View) -> Unit):
    ListAdapter<ClientsDebtState, ClientsAdapter.ViewHolder>(
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

    private fun setItemView(client: ClientsDebtState, holder: ViewHolder) {
        with(holder) {
            containerView.setOnClickListener { onClick(containerView) }
            clientItem_tvName.text = client.client.fullNameString
            clientItem_tvSum.text = String.format(containerView.context.getString(
                when {
                    abs(client.totalSum) < 0.01 -> R.string.clientItem_tvSum_ph_no
                    client.totalSum < 0 -> R.string.clientItem_tvSum_ph_pay
                    else -> R.string.clientItem_tvSum_ph_get
                }
            ), (abs(client.totalSum) * 100).roundToInt() / 100.0)

            clientItem_tvRecordsCount.text = String.format(containerView.context.getString(
                if (client.debtsCount != 0 && abs(client.totalSum) < 0.01)
                    R.string.clientItem_tvRecordsCount_ph_empty
                else if (client.debtsCount == 1)
                    R.string.clientItem_tvRecordsCount_ph_one
                else
                    R.string.clientItem_tvRecordsCount_ph
            ), client.debtsCount)
        }
    }

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer
}