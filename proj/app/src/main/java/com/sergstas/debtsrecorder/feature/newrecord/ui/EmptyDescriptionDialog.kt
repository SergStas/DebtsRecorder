package com.sergstas.debtsrecorder.feature.newrecord.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sergstas.debtsrecorder.R
import kotlinx.android.synthetic.main.dialog_new_entry_empty_description.*

class EmptyDescriptionDialog: AppCompatActivity() {
    companion object {
        const val REQUEST_CODE = 0
        const val RESULT_KEY = "RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_new_entry_empty_description)

        newRecord_dialog_bBack.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(RESULT_KEY, false)
            })
            finish()
        }

        newRecord_dialog_bSubmit.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(RESULT_KEY, true)
            })
            finish()
        }
    }
}