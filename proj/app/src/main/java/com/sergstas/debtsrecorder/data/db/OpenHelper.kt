package com.sergstas.debtsrecorder.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class OpenHelper(private val context: Context?, private val name: String, private val query: String) :
    SQLiteOpenHelper(context, name, null, VERSION) {
    companion object {
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w("SQLite", "Update from $oldVersion to $newVersion");
        db.execSQL("DROP TABLE IF IT EXISTS " + name)
        onCreate(db)
    }
}