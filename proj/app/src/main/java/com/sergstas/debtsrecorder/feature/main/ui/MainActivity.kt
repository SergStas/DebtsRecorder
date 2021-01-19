package com.sergstas.debtsrecorder.feature.main.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.feature.clients.ui.ClientsActivity
import com.sergstas.debtsrecorder.feature.debts.enums.DebtsActivityType
import com.sergstas.debtsrecorder.feature.debts.ui.DebtsActivity
import com.sergstas.debtsrecorder.feature.main.data.MainDao
import com.sergstas.debtsrecorder.feature.main.data.MainDaoImpl
import com.sergstas.debtsrecorder.feature.main.enums.MainMessage
import com.sergstas.debtsrecorder.feature.main.presntation.MainPresenter
import com.sergstas.debtsrecorder.feature.main.presntation.MainView
import com.sergstas.debtsrecorder.feature.newrecord.ui.NewRecordActivity
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity: MvpAppCompatActivity(), MainView {
    companion object {
        private const val NEW_DEBT_REQUEST_CODE = 0
    }

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
        main_tvMessage.setOnClickListener {
            startActivity(Intent(this, ClientsActivity::class.java))
        }

        main_bNewRecord.setOnClickListener {
            startActivityForResult(
                Intent(this, NewRecordActivity::class.java),
                NEW_DEBT_REQUEST_CODE
            )
        }

        main_bAllRecords.setOnClickListener {
            startActivity(DebtsActivity.getIntent(this, DebtsActivityType.ALL_DEBTS, null))
        }

        main_bClients.setOnClickListener {
            startActivity(Intent(this, ClientsActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                NEW_DEBT_REQUEST_CODE -> showMessage(MainMessage.RECORD_CREATED)
            }
    }

    override fun showLoading(b: Boolean) {
        main_pb.isVisible = b
    }

    override fun setDebtorsInfo(to: Int, from: Int) {
       main_tvMessage.text = String.format(getString(R.string.main_tvMessage_ph), from, to)
    }

    override fun showMessage(message: MainMessage) {
        val text = getString(when(message) {
            MainMessage.RECORD_CREATED -> R.string.mainMessage_recordCreated
        })

        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}