package com.sergstas.debtsrecorder.feature.newrecord.ui

import android.app.Dialog
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
import com.sergstas.debtsrecorder.feature.newrecord.data.NewRecordDaoImpl
import com.sergstas.debtsrecorder.feature.newrecord.enums.ValidationError
import com.sergstas.debtsrecorder.feature.newrecord.presentation.NewRecordPresenter
import com.sergstas.debtsrecorder.feature.newrecord.presentation.NewRecordView
import kotlinx.android.synthetic.main.activity_new_record.*
import kotlinx.android.synthetic.main.dialog_new_entry_empty_description.*
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

    override fun setClientsSpinner(clients: List<Client>) {
        val labels = clients.map { c -> "${c.lastName} ${c.firstName}" }
        newRecord_spin.adapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, labels)
            .apply { setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item) }

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
            ValidationError.CLIENT_IS_NULL -> getString(R.string.newRecord_toast_clientIsNull)
            ValidationError.INCORRECT_SUM -> getString(R.string.newRecord_toast_incorrectSum)
        }

        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun showEmptyDescriptionWarning() {
        startActivityForResult(
            Intent(this, EmptyDescriptionDialog::class.java),
            EmptyDescriptionDialog.REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EmptyDescriptionDialog.REQUEST_CODE &&
            data!!.getBooleanExtra(EmptyDescriptionDialog.RESULT_KEY, false))
            _presenter.validate(
                newRecord_editSum.text.toString(),
                newRecord_rbFrom.isChecked,
                _client,
                newRecord_editDestDate.text.toString(),
                newRecord_editDescription.text.toString(),
                true
            )
    }

    override fun close() {
        finish()
    }
}

