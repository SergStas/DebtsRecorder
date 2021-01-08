package com.sergstas.debtsrecorder.feature.debts.ui

import android.app.Activity
import android.app.Instrumentation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sergstas.debtsrecorder.R
import kotlinx.android.synthetic.main.dialog_confirmation_remove.*

class ConfirmationRemoveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_confirmation_remove)

        setResult(Activity.RESULT_CANCELED)

        dialogRemove_bNo.setOnClickListener {
            finish()
        }

        dialogRemove_bYes.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}