package com.sergstas.debtsrecorder.data.db

import android.content.Context
import android.database.Cursor
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record
import java.lang.Exception

class DBHolder(private val _context: Context) {
    companion object {
        private const val RECORDS_TABLE_NAME = "records"
        private const val RECORDS_ID_NAME = "record_id"
        private const val RECORDS_SUM_NAME = "sum"
        private const val RECORDS_CLIENT_ID_NAME = "client_id"
        private const val RECORDS_DATE_NAME = "date"
        private const val RECORDS_CLIENT_PAYS_NAME = "client_pays"
        private const val RECORDS_DEST_DATE_NAME = "destination_date"
        private const val RECORDS_DESCRIPTION_NAME = "description"

        private const val CLIENTS_TABLE_NAME = "clients"
        private const val CLIENTS_ID_NAME = "client_id"
        private const val CLIENTS_FIRST_NAME_NAME = "first_name"
        private const val CLIENTS_LAST_NAME_NAME = "last_name"

        private const val CREATE_DEBTS_QUERY =
            "create table $RECORDS_TABLE_NAME (" +
                "$RECORDS_ID_NAME integer primary key autoincrement," +
                "$RECORDS_CLIENT_ID_NAME integer not null," +
                "$RECORDS_SUM_NAME double not null" +
                "$RECORDS_DATE_NAME string not null" +
                "$RECORDS_CLIENT_PAYS_NAME integer not null," +
                "$RECORDS_DEST_DATE_NAME string," +
                "$RECORDS_DESCRIPTION_NAME string" +
            ")"

        private const val CREATE_CLIENTS_QUERY =
            "create table $CLIENTS_TABLE_NAME (" +
                "$CLIENTS_ID_NAME primary key autoincrement" +
                "$CLIENTS_FIRST_NAME_NAME string not null" +
                "$CLIENTS_LAST_NAME_NAME string not null" +
            ")"

        private const val GET_ALL_QUERY =
            "select * from $RECORDS_TABLE_NAME"

        private const val FIND_CLIENT_QUERY =
            "select * from $CLIENTS_TABLE_NAME where $CLIENTS_ID_NAME = %s"
    }

    private val _debtsHelper = OpenHelper(_context, RECORDS_TABLE_NAME, CREATE_DEBTS_QUERY)
    private val _clientsHelper = OpenHelper(_context, CLIENTS_TABLE_NAME, CREATE_CLIENTS_QUERY)

    fun getAllDebtsRecords(): List<Record> =
        try {
            val cursor = _debtsHelper.readableDatabase.rawQuery(GET_ALL_QUERY, null)
            val result = mutableListOf<Record>()
            while (cursor.moveToNext())
                result.add(getRecord(cursor))
            result
        }
        catch (e: Exception) {
            emptyList()
        }

    private fun getClientInfo(id: Int): Client {
        val cursor = _clientsHelper.readableDatabase
            .rawQuery(String.format(FIND_CLIENT_QUERY, id), null)
            .apply { moveToNext() }
        with(cursor) {
            return Client(
                id = getInt(getColumnIndex(CLIENTS_ID_NAME)),
                firstName = getString(getColumnIndex(CLIENTS_FIRST_NAME_NAME)),
                lastName = getString(getColumnIndex(CLIENTS_LAST_NAME_NAME))
            )
        }
    }

    private fun getRecord(cursor: Cursor): Record {
        val client = getClientInfo(cursor.getInt(cursor.getColumnIndex(RECORDS_CLIENT_ID_NAME)))
        with(cursor) {
            return Record(
                id = getInt(getColumnIndex(RECORDS_ID_NAME)),
                sum = getDouble(getColumnIndex(RECORDS_SUM_NAME)),
                clientFirstName = client.firstName,
                clientLastName = client.lastName,
                doesClientPay = getInt(getColumnIndex(RECORDS_CLIENT_PAYS_NAME)) != 0,
                date = getString(getColumnIndex(RECORDS_DATE_NAME)),
                destDate = getString(getColumnIndex(RECORDS_DEST_DATE_NAME)),
                description = getString(getColumnIndex(RECORDS_DESCRIPTION_NAME))
            )
        }
    }
}