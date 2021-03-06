package com.sergstas.debtsrecorder.feature.newrecord.ui

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
import com.sergstas.debtsrecorder.feature.newclient.ui.NewClientActivity
import com.sergstas.debtsrecorder.feature.newrecord.data.NewRecordDaoImpl
import com.sergstas.debtsrecorder.feature.newrecord.enums.ValidationError
import com.sergstas.debtsrecorder.feature.newrecord.presentation.NewRecordPresenter
import com.sergstas.debtsrecorder.feature.newrecord.presentation.NewRecordView
import com.sergstas.debtsrecorder.feature.newrecord.ui.dialogs.EmptyDescriptionDialog
import kotlinx.android.synthetic.main.activity_new_record.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class NewRecordActivity : MvpAppCompatActivity(), NewRecordView {
    companion object {
        private const val DEFAULT_CLIENT_KEY = "CLIENT"

        private const val DIALOG_REQUEST_CODE = 0
        private const val NEW_CLIENT_REQUEST_CODE = 1

        fun getIntent(context: Context, defaultClient: Client?) =
            Intent(context, NewRecordActivity::class.java).apply {
                putExtra(DEFAULT_CLIENT_KEY, defaultClient)
            }
    }

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
            startActivityForResult(Intent(this, NewClientActivity::class.java), NEW_CLIENT_REQUEST_CODE)
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
        val labels = clients.map { c -> c.fullNameString }
        newRecord_spin.adapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, labels)
            .apply { setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item) }

        if (intent?.getParcelableExtra<Client>(DEFAULT_CLIENT_KEY) != null)
            with(newRecord_spin) {
                setSelection(
                    (adapter as ArrayAdapter<String>)
                        .getPosition(intent!!.getParcelableExtra<Client>(DEFAULT_CLIENT_KEY)!!.fullNameString)
                )
            }

            newRecord_spin.isVisible = labels.isNotEmpty()
            newRecord_tvNoClient.isVisible = labels.isEmpty()

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

    override fun close() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showClientAddedMessage() {
        Toast.makeText(this, getString(R.string.newRecord_message_clientAdded), Toast.LENGTH_LONG).show()
    }
}

