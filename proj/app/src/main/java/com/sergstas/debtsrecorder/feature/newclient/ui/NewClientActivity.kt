package com.sergstas.debtsrecorder.feature.newclient.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.feature.newclient.presentation.NewClientView
import com.sergstas.debtsrecorder.feature.newrecord.enums.ValidationError
import kotlinx.android.synthetic.main.activity_new_client.*
import moxy.MvpAppCompatActivity

class NewClientActivity : MvpAppCompatActivity(), NewClientView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_client)
    }

    override fun close() {
        finish()
    }

    override fun setListeners() {
        newClient_bSubmit.setOnClickListener {

        }
    }

    override fun showValidationError(error: ValidationError) {
        val text = ""
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun showLoading(b: Boolean) {
        newClient_pb.isVisible = b
    }
}

