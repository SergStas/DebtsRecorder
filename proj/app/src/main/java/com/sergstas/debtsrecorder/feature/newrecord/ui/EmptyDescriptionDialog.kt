package com.sergstas.debtsrecorder.feature.newrecord.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sergstas.debtsrecorder.R
import kotlinx.android.synthetic.main.dialog_new_entry_empty_description.*

class EmptyDescriptionDialog: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_new_entry_empty_description)

        setResult(Activity.RESULT_CANCELED)

        newRecord_dialog_bBack.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        newRecord_dialog_bSubmit.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}