package com.sergstas.debtsrecorder.feature.newrecord.presentation

import android.content.Context
import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.newrecord.enums.ValidationError
import com.sergstas.debtsrecorder.feature.newrecord.data.NewRecordDao
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import java.lang.Exception
import java.sql.Date

class NewRecordPresenter(private val _context: Context, private val _dao: NewRecordDao): MvpPresenter<NewRecordView>() {
    private var _clients: List<Client> = emptyList()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setListeners()
        loadClientsData()
    }

    fun validate(
        sumStr: String,
        clientPays: Boolean,
        client: String?,
        destDate: String?,
        description: String?,
        ignoreEmptyDescription: Boolean
    ) {
        viewState.showLoading(true)

        try {
            val sum = sumStr.toDouble()
            if (client == null)
                viewState.showValidationError(ValidationError.CLIENT_IS_NULL)
            else if (!ignoreEmptyDescription && description.isNullOrEmpty())
                viewState.showEmptyDescriptionWarning()
            else {
                val date = Date(System.currentTimeMillis()).toString().split('-')
                if (_dao.addNewRecord(Record(
                    sum,
                    client.split(' ')[0],
                    client.split(' ')[1],
                    clientPays,
                    "${date[2]}.${date[1]}.${date[0]}",
                    destDate,
                    description
                )))
                    viewState.close()
                else
                    viewState.showValidationError(ValidationError.UNKNOWN)
            }
        }
        catch (e: Exception) {
            viewState.showValidationError(ValidationError.INCORRECT_SUM)
        }
        viewState.showLoading(false)
    }

    private fun loadClientsData() {
        presenterScope.launch {
            viewState.showLoading(true)
            _clients = _dao.getClients()
            viewState.setClientsSpinner(_clients)
            viewState.showLoading(false)
        }
    }

    fun processAddingNewClient() {
        loadClientsData()
        viewState.showClientAddedMessage()
    }
}