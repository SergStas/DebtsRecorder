package com.sergstas.debtsrecorder.feature.debts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sergstas.debtsrecorder.R
import com.sergstas.debtsrecorder.data.db.DBHolder
import com.sergstas.debtsrecorder.feature.debts.data.DebtsDaoImpl

class DebtsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debts)

        supportFragmentManager.beginTransaction().replace(R.id.debts_root, DebtsListFragment(
            DebtsDaoImpl(DBHolder(this)) //TODO: inject
        )).commit()
    }
}