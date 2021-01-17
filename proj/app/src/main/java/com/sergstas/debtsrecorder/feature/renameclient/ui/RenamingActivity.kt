package com.sergstas.debtsrecorder.feature.renameclient.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.feature.renameclient.data.RenamingDaoImpl
import com.sergstas.debtsrecorder.feature.renameclient.enums.RenamingValidationError
import com.sergstas.debtsrecorder.feature.renameclient.presentation.RenamingPresenter
import com.sergstas.debtsrecorder.feature.renameclient.presentation.RenamingView
import kotlinx.android.synthetic.main.activity_new_client.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class RenamingActivity : MvpAppCompatActivity(), RenamingView {
    companion object {
        private const val NAME_ARG_KEY = "NAME"

        fun getIntent(context: Context, name: String) =
            Intent(context, RenamingActivity::class.java).apply {
                putExtra(NAME_ARG_KEY, name)
            }
    }

    private val _presenter: RenamingPresenter by moxyPresenter {
        RenamingPresenter(
            RenamingDaoImpl(DBHolder(this)),
            intent.getStringExtra(NAME_ARG_KEY)!!
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_client)

        newClient_bSubmit.setOnClickListener {
            _presenter.validate(
                newClient_editFirstName.text.toString(),
                newClient_editLastName.text.toString()
            )
        }
    }

    override fun setClient(client: Client) {
        newClient_editFirstName.setText(client.firstName)
        newClient_editLastName.setText(client.lastName)
    }

    override fun showValidationError(error: RenamingValidationError) {
        val text =  getString( when (error) {
            RenamingValidationError.FIRST_NAME_IS_NULL -> R.string.ve_renaming_firstNameIsNull
            RenamingValidationError.LAST_NAME_IS_NULL -> R.string.ve_renaming_lastNameIsNull
            RenamingValidationError.NAME_CONTAIN_SPACES -> R.string.ve_renaming_spaces
            RenamingValidationError.CLIENT_ALREADY_EXISTS -> R.string.ve_renaming_clientExists
            RenamingValidationError.UNKNOWN_ERROR -> R.string.ve_renaming_unknownError
        })

        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun close() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showLoading(b: Boolean) {
        newClient_pb.isVisible = b
    }
}

