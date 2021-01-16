package com.sergstas.debtsrecorder.feature.clientrecords.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.clientrecords.adapters.ClientRecordsAdapter
import com.sergstas.debtsrecorder.feature.clientrecords.data.ClientRecordsDaoImpl
import com.sergstas.debtsrecorder.feature.clientrecords.presentation.ClientRecordsPresenter
import com.sergstas.debtsrecorder.feature.clientrecords.presentation.ClientRecordsView
import kotlinx.android.synthetic.main.activity_client_records.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class ClientRecordsActivity : MvpAppCompatActivity(), ClientRecordsView {
    companion object {
        private const val CLIENT_ARG_KEY = "CLIENT"

        fun getIntent(context: Context, client: Client) =
            Intent(context, ClientRecordsActivity::class.java).apply {
                putExtra(CLIENT_ARG_KEY, client)
            }
    }

    private var _client: Client? = null
    private lateinit var _adapter: ClientRecordsAdapter

    private val _presenter: ClientRecordsPresenter by moxyPresenter {
        ClientRecordsPresenter(ClientRecordsDaoImpl(DBHolder(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_records)

        _client = intent.getParcelableExtra(CLIENT_ARG_KEY)
        _presenter.passClientInfo(_client!!)
    }

    override fun setList(list: List<Record>) {
        _adapter.submitList(list)
    }

    override fun showLoading(b: Boolean) {
        clRec_pb.isVisible = b
    }

    override fun setView() {
        clRec_tvHeader.text = String.format(getString(R.string.clRec_tvHeader_ph), _client!!.fullNameString)

        with(clRec_rv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ClientRecordsAdapter().also { _adapter = it }
        }
    }

    override fun showEmptyListMessage(b: Boolean) {
        clRec_tvEmptyList.isVisible = b
    }
}

