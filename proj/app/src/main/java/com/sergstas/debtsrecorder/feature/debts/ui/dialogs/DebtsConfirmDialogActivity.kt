package com.sergstas.debtsrecorder.feature.debts.ui.dialogs

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sergstas.debtsrecorder.R
import kotlinx.android.synthetic.main.dialog_confirmation_debt_item.*

class DebtsConfirmDialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_confirmation_debt_item)

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