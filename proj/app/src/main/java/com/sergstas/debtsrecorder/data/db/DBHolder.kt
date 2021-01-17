package com.sergstas.debtsrecorder.data.db

import android.content.Context
import android.database.Cursor
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record
import java.lang.Exception

class DBHolder(_context: Context) {
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
                "$RECORDS_ID_NAME integer primary key autoincrement, " +
                "$RECORDS_CLIENT_ID_NAME integer not null, " +
                "$RECORDS_SUM_NAME double not null, " +
                "$RECORDS_DATE_NAME string not null, " +
                "$RECORDS_CLIENT_PAYS_NAME integer not null," +
                "$RECORDS_DEST_DATE_NAME string," +
                "$RECORDS_DESCRIPTION_NAME string" +
            ")"

        private const val CREATE_CLIENTS_QUERY =
            "create table $CLIENTS_TABLE_NAME (" +
                "$CLIENTS_ID_NAME integer primary key autoincrement, " +
                "$CLIENTS_FIRST_NAME_NAME string not null, " +
                "$CLIENTS_LAST_NAME_NAME string not null" +
            ")"

        private const val GET_ALL_QUERY =
            "select * from $RECORDS_TABLE_NAME"

        private const val FIND_CLIENT_BY_ID_QUERY =
            "select * from $CLIENTS_TABLE_NAME where $CLIENTS_ID_NAME = %s"

        private const val FIND_CLIENT_QUERY =
            "select * from $CLIENTS_TABLE_NAME where $CLIENTS_FIRST_NAME_NAME = '%s' " +
                "and $CLIENTS_LAST_NAME_NAME = '%s'"

        private const val ADD_CLIENT_QUERY =
            "insert into $CLIENTS_TABLE_NAME values (null, '%s', '%s')"

        private const val GET_ALL_CLIENTS_QUERY =
            "select * from $CLIENTS_TABLE_NAME"

        private const val ADD_RECORD_QUERY =
            "insert into $RECORDS_TABLE_NAME values (null, %s, %s, '%s', %s, %s, %s)"

        private const val CLEAR_CLIENTS_QUERY =
            "delete from $CLIENTS_TABLE_NAME"

        private const val FIND_RECORD_ID_QUERY =
            "select $RECORDS_ID_NAME from $RECORDS_TABLE_NAME where " +
                "$RECORDS_SUM_NAME = %s and $RECORDS_CLIENT_ID_NAME = %s and $RECORDS_CLIENT_PAYS_NAME = %s " +
                "and $RECORDS_DATE_NAME = '%s' and $RECORDS_DEST_DATE_NAME %s and $RECORDS_DESCRIPTION_NAME %s"

        private const val REMOVE_RECORD_BY_ID_QUERY =
            "delete from $RECORDS_TABLE_NAME where $RECORDS_ID_NAME = %s"

        private const val UPDATE_RECORD_QUERY =
            "update $RECORDS_TABLE_NAME set " +
                "$RECORDS_CLIENT_ID_NAME = %s, " +
                "$RECORDS_SUM_NAME = %s, " +
                "$RECORDS_CLIENT_PAYS_NAME = %s, " +
                "$RECORDS_DEST_DATE_NAME = %s, " +
                "$RECORDS_DESCRIPTION_NAME = %s " +
            "where $RECORDS_ID_NAME = %s"

        private const val RENAME_CLIENT_QUERY =
            "update $CLIENTS_TABLE_NAME set " +
                "$CLIENTS_FIRST_NAME_NAME = '%s', " +
                "$CLIENTS_LAST_NAME_NAME = '%s' " +
            "where $CLIENTS_ID_NAME = %s"

        private const val REMOVE_CLIENT_QUERY =
            "delete from $CLIENTS_TABLE_NAME " +
            "where $CLIENTS_ID_NAME = %s"

        private const val CLEANUP_RECORDS_QUERY =
            "delete from $RECORDS_TABLE_NAME " +
            "where $RECORDS_CLIENT_ID_NAME = %s"
    }

    private val _recordsHelper = OpenHelper(_context, RECORDS_TABLE_NAME, CREATE_DEBTS_QUERY)
    private val _clientsHelper = OpenHelper(_context, CLIENTS_TABLE_NAME, CREATE_CLIENTS_QUERY)

    fun getAllDebtsRecords(): List<Record> =
        try {
            val cursor = _recordsHelper.readableDatabase.rawQuery(GET_ALL_QUERY, null)
            val result = mutableListOf<Record>()
            while (cursor.moveToNext())
                result.add(getRecord(cursor))
            result
        }
        catch (e: Exception) {
            emptyList()
        }

    fun removeAllClients() {
        _clientsHelper.writableDatabase.execSQL(CLEAR_CLIENTS_QUERY)
    }

    fun getClientsId(client: Client): Int? =
        try {
            val query = String.format(FIND_CLIENT_QUERY, client.firstName, client.lastName)
            val cursor = _clientsHelper.readableDatabase.rawQuery(query, null)

            with(cursor.apply { moveToNext() }) {
                getInt(getColumnIndex(CLIENTS_ID_NAME))
            }
        }
        catch (e: Exception) {
            null
        }

    fun addClient(client: Client): Boolean =
        try {
            val query = String.format(ADD_CLIENT_QUERY, client.firstName, client.lastName)
            _clientsHelper.writableDatabase.execSQL(query)
            true
        }
        catch (e: Exception) {
            false
        }

    fun getClients(): List<Client> =
        try {
            val cursor = _clientsHelper.readableDatabase.rawQuery(GET_ALL_CLIENTS_QUERY, null)
            val result = mutableListOf<Client>()
            while (cursor.moveToNext())
                with(cursor){
                    result.add(Client(
                        getString(getColumnIndex(CLIENTS_FIRST_NAME_NAME)),
                        getString(getColumnIndex(CLIENTS_LAST_NAME_NAME))
                    ))
                }
            result
        }
        catch (e: Exception) {
            emptyList()
        }

    fun addRecord(record: Record): Boolean =
        try {
            val clientId = getClientsId(Client(record.clientFirstName, record.clientLastName))
            val description = if (record.description.isNullOrEmpty()) "null" else "'${record.description}'"
            val destDate = if(record.destDate.isNullOrEmpty()) "null" else "'${record.destDate}'"
            val clientPays = if (record.doesClientPay) 1 else 0
            val query = String.format(
                ADD_RECORD_QUERY,
                clientId,
                record.sum,
                record.date,
                clientPays,
                destDate,
                description
            )

            _recordsHelper.writableDatabase.execSQL(query)
            true
        }
        catch (e: Exception) {
            false
        }

    fun removeRecord(record: Record): Boolean =
        try {
            val id = findRecordsId(record)
            _recordsHelper.writableDatabase.execSQL(String.format(REMOVE_RECORD_BY_ID_QUERY, id))
            id != -1
        }
        catch (e: Exception) {
            false
        }

    private fun getClientInfo(id: Int): Client {
        val cursor = _clientsHelper.readableDatabase
            .rawQuery(String.format(FIND_CLIENT_BY_ID_QUERY, id), null)
            .apply { moveToNext() }
        with(cursor) {
            return Client(
                firstName = getString(getColumnIndex(CLIENTS_FIRST_NAME_NAME)),
                lastName = getString(getColumnIndex(CLIENTS_LAST_NAME_NAME))
            )
        }
    }

    private fun getRecord(cursor: Cursor): Record {
        val client = getClientInfo(cursor.getInt(cursor.getColumnIndex(RECORDS_CLIENT_ID_NAME)))
        with(cursor) {
            return Record(
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

    private fun findRecordsId(record: Record): Int =
        try {
            val clientId = getClientsId(Client(record.clientFirstName, record.clientLastName))
            val description = if (record.description.isNullOrEmpty()) "is null" else "= '${record.description}'"
            val destDate = if (record.destDate.isNullOrEmpty()) "is null" else "= '${record.destDate}'"
            val clientPays = if (record.doesClientPay) 1 else 0
            val query = String.format(
                FIND_RECORD_ID_QUERY,
                record.sum, clientId!!, clientPays, record.date, destDate, description)
            val cursor = _recordsHelper.readableDatabase.rawQuery(query, null)
            with(cursor.apply { moveToNext() }) {
                getInt(getColumnIndex(RECORDS_ID_NAME))
            }
        }
        catch (e: Exception) {
            -1
        }

    fun updateRecord(old: Record, new: Record): Boolean  =
        try {
            val clientId = getClientsId(Client(new.clientFirstName, new.clientLastName))
            val description = if (new.description.isNullOrEmpty()) "null" else "'${new.description}'"
            val destDate = if (new.destDate.isNullOrEmpty()) "null" else "'${new.destDate}'"
            val clientPays = if (new.doesClientPay) 1 else 0
            val oldId = findRecordsId(old)
            val query = String.format(UPDATE_RECORD_QUERY,
                clientId,
                new.sum,
                clientPays,
                destDate,
                description,
                oldId
            )
            _recordsHelper.writableDatabase.execSQL(query)
            oldId != -1
        }
        catch (e: Exception) {
            false
        }

    fun getClientsInfo(): Map<Client, List<Record>> =
        try {
            val map = getAllDebtsRecords().groupBy { r -> r.clientString }
                .mapKeys { e -> Client(e.key.split(" ")[0], e.key.split(" ")[1]) }.toMutableMap()
            for (c in getClients())
                if (!map.containsKey(c))
                    map[c] = emptyList()
            map
        }
        catch (e: Exception) {
            emptyMap()
        }

    fun getClientsRecords(client: Client): List<Record> =
        getAllDebtsRecords().filter { r -> r.clientString == client.fullNameString }

    fun renameClient(old: Client, new: Client): Boolean =
        try {
            val clientId = getClientsId(old)
            val query = String.format(RENAME_CLIENT_QUERY, new.firstName, new.lastName, clientId)
            _clientsHelper.writableDatabase.execSQL(query)
            clientId != null
        }
        catch (e: Exception) {false}

    fun cleanupRecords(client: Client): Boolean =
        try {
            val id = getClientsId(client)
            val query = String.format(CLEANUP_RECORDS_QUERY, id)
            _recordsHelper.writableDatabase.execSQL(query)
            id != null
        }
        catch (e: Exception) {
            false
        }

    fun removeClient(client: Client): Boolean =
        try {
            val id = getClientsId(client)
            val cleanup = cleanupRecords(client)
            val query = String.format(REMOVE_CLIENT_QUERY, id)
            _clientsHelper.writableDatabase.execSQL(query)
            id != null && cleanup
        }
        catch (e: Exception) {false}
}