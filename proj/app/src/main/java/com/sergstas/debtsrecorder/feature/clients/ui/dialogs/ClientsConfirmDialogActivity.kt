package com.sergstas.debtsrecorder.feature.clients.ui.dialogs

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sergstas.debtsrecorder.R
import kotlinx.android.synthetic.main.dialog_confirmation_client_item.*

class ClientsConfirmDialogActivity : AppCompatActivity() {
    companion object {
        private const val CONTENT_ARG_KEY = "CONTENT"
        const val CLIENT_ARG_KEY = "CLIENT"

        fun getIntent(context: Context, client: String, content: String) =
            Intent(context, ClientsConfirmDialogActivity::class.java).apply {
                putExtra(CONTENT_ARG_KEY, content)
                putExtra(CLIENT_ARG_KEY, client)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_confirmation_client_item)

        confirmCI_tvContent.text = intent.getStringExtra(CONTENT_ARG_KEY)

        confirmCI_bYes.setOnClickListener {
            setResult(Activity.RESULT_OK,
                Intent().apply {
                    putExtra(CLIENT_ARG_KEY,
                    intent.getStringExtra(CLIENT_ARG_KEY))
                })
            finish()
        }

        confirmCI_bNo.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}