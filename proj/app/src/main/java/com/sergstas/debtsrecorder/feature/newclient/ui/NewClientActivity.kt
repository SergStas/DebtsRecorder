package com.sergstas.debtsrecorder.feature.newclient.ui

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.feature.newclient.data.NewClientDaoImpl
import com.sergstas.debtsrecorder.feature.newclient.presentation.NewClientPresenter
import com.sergstas.debtsrecorder.feature.newclient.presentation.NewClientView
import com.sergstas.debtsrecorder.feature.newrecord.enums.ValidationError
import kotlinx.android.synthetic.main.activity_new_client.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class NewClientActivity : MvpAppCompatActivity(), NewClientView {

    private val _presenter: NewClientPresenter by moxyPresenter {
        NewClientPresenter(NewClientDaoImpl(DBHolder(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_client)
        setResult(Activity.RESULT_CANCELED)
    }

    override fun close() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun setListeners() {
        newClient_bSubmit.setOnClickListener {
            _presenter.validate(
                newClient_editFirstName.text.toString(),
                newClient_editLastName.text.toString()
            )
        }
    }

    override fun showValidationError(error: ValidationError) {
        val text = when(error) {
            ValidationError.FIRST_NAME_IS_NULL -> getString(R.string.error_firstNameIsNull)
            ValidationError.LAST_NAME_IS_NULL -> getString(R.string.error_lastNameIsNull)
            ValidationError.CLIENT_ALREADY_EXISTS -> getString(R.string.error_clientExists)
            ValidationError.NAME_CONTAINS_SPACES -> getString(R.string.error_nameContainsSpaces)
            else -> getString(R.string.error_unknownError)
        }

        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun showLoading(b: Boolean) {
        newClient_pb.isVisible = b
    }
}

