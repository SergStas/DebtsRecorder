package com.sergstas.debtsrecorder.feature.main.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.feature.clients.ui.ClientsActivity
import com.sergstas.debtsrecorder.feature.debts.ui.DebtsActivity
import com.sergstas.debtsrecorder.feature.main.data.MainDaoImpl
import com.sergstas.debtsrecorder.feature.main.presntation.MainPresenter
import com.sergstas.debtsrecorder.feature.main.presntation.MainView
import com.sergstas.debtsrecorder.feature.newrecord.ui.NewRecordActivity
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {
    private val _presenter: MainPresenter by moxyPresenter {
        MainPresenter(MainDaoImpl(DBHolder(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        _presenter.updateDebtorsInfo()
    }

    override fun setListeners() {
        main_bNewRecord.setOnClickListener {
            startActivity(Intent(this, NewRecordActivity::class.java))
        }

        main_bAllRecords.setOnClickListener {
            startActivity(Intent(this, DebtsActivity::class.java))
        }

        main_bClients.setOnClickListener {
            startActivity(Intent(this, ClientsActivity::class.java))
        }
    }

    override fun showLoading(b: Boolean) {
        main_pb.isVisible = b
    }

    override fun setDebtorsInfo(to: Int, from: Int) {

    }
}