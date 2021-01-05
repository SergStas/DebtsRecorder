package com.sergstas.debtsrecorder.feature.newrecord.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.feature.newrecord.data.NewRecordDaoImpl
import com.sergstas.debtsrecorder.feature.newrecord.presentation.NewRecordPresenter
import com.sergstas.debtsrecorder.feature.newrecord.presentation.NewRecordView
import kotlinx.android.synthetic.main.activity_new_record.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class NewRecordActivity : MvpAppCompatActivity(), NewRecordView {

    private val _presenter: NewRecordPresenter by moxyPresenter {
        NewRecordPresenter(this, NewRecordDaoImpl(DBHolder(this)))
    }

    private var _client: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_record)
    }

    override fun setListeners() {
        newRecord_bNewClient.setOnClickListener {

        }
    }

    override fun setClientsSpinner(clients: List<Client>) {
        val labels = clients.map { c -> "${c.lastName} ${c.firstName}" }
        newRecord_spin.adapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, labels)
            .apply { setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item) }

        newRecord_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                _client = parent!!.getItemAtPosition(position) as String
            }
        }
    }

    override fun showLoading(b: Boolean) {
        newRecord_pb.isVisible = b
    }
}

