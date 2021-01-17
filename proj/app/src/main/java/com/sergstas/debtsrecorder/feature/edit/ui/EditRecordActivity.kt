package com.sergstas.debtsrecorder.feature.edit.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.edit.data.EditRecordDaoImpl
import com.sergstas.debtsrecorder.feature.edit.presentation.EditRecordPresenter
import com.sergstas.debtsrecorder.feature.edit.presentation.EditRecordView
import com.sergstas.debtsrecorder.feature.newclient.ui.NewClientActivity
import com.sergstas.debtsrecorder.feature.newrecord.enums.ValidationError
import com.sergstas.debtsrecorder.feature.newrecord.ui.dialogs.EmptyDescriptionDialog
import kotlinx.android.synthetic.main.activity_new_record.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class EditRecordActivity: MvpAppCompatActivity(), EditRecordView {
    companion object {
        private const val NEW_CLIENT_REQUEST_CODE = 0
        private const val DIALOG_REQUEST_CODE = 1

        private const val RECORD_ARG_KEY = "RECORD"

        fun getIntent(context: Context, record: Record) =
            Intent(context, EditRecordActivity::class.java).apply {
                putExtra(RECORD_ARG_KEY, record)
            }
    }

    private val _presenter: EditRecordPresenter by moxyPresenter {
        EditRecordPresenter(EditRecordDaoImpl(DBHolder(this)))
    }

    private lateinit var _record: Record
    private var _client: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_record)

        _record = intent.getParcelableExtra(RECORD_ARG_KEY)!!
        _presenter.passRecord(_record)
    }

    override fun setView() {
        newRecord_tvTitle.text = getString(R.string.newRecord_tvTitle_edit)
        newRecord_rbFrom.isChecked = _record.doesClientPay
        newRecord_rbTo.isChecked = !_record.doesClientPay
        newRecord_editSum.setText(_record.sum.toString())
        newRecord_editDestDate.setText(_record.destDate)
        newRecord_editDescription.setText(_record.description)

        newRecord_bNewClient.setOnClickListener {
            startActivityForResult(Intent(this, NewClientActivity::class.java),
                NEW_CLIENT_REQUEST_CODE
            )
        }

        newRecord_bSubmit.setOnClickListener {
            _presenter.validate(
                newRecord_editSum.text.toString(),
                newRecord_rbFrom.isChecked,
                _client,
                newRecord_editDestDate.text.toString(),
                newRecord_editDescription.text.toString(),
                false
            )
        }
    }

    override fun setClientsSpinner(list: List<Client>) {
        val labels = list.map { c -> c.fullNameString }
        val arrAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, labels)
                .apply { setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item) }

        with (newRecord_spin) {
            adapter = arrAdapter
            setSelection(arrAdapter.getPosition(_record.clientString))
        }

        if (labels.isEmpty()) {
            newRecord_spin.isVisible = false
            newRecord_tvNoClient.isVisible = true
        }

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

    override fun showValidationError(error: ValidationError) {
        val text = when (error) {
            ValidationError.CLIENT_IS_NULL -> getString(R.string.error_clientIsNull)
            ValidationError.INCORRECT_SUM -> getString(R.string.error_incorrectSum)
            else -> getString(R.string.error_unknownError)
        }

        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun close() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showClientAddedMessage() {
        Toast.makeText(this, getString(R.string.newRecord_message_clientAdded), Toast.LENGTH_LONG).show()
    }

    override fun showEmptyDescriptionWarning() {
        startActivityForResult(
            Intent(this, EmptyDescriptionDialog::class.java),
            DIALOG_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DIALOG_REQUEST_CODE &&
            resultCode == Activity.RESULT_OK)
            _presenter.validate(
                newRecord_editSum.text.toString(),
                newRecord_rbFrom.isChecked,
                _client,
                newRecord_editDestDate.text.toString(),
                newRecord_editDescription.text.toString(),
                true
            )
        else if (requestCode == NEW_CLIENT_REQUEST_CODE &&
            resultCode == Activity.RESULT_OK)
            _presenter.processAddingNewClient()

    }
}

