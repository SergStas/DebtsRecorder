package com.sergstas.debtsrecorder.feature.edit.presentation

import com.sergstas.debtsrecorder.domain.entity.Record
import com.sergstas.debtsrecorder.feature.edit.data.EditRecordDao
import com.sergstas.debtsrecorder.feature.newrecord.enums.ValidationError
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import java.lang.Exception
import java.sql.Date

class EditRecordPresenter(private val _dao: EditRecordDao): MvpPresenter<EditRecordView>() {
    private var _old: Record? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setView()
        loadClientsData()
    }

    private fun loadClientsData() {
        presenterScope.launch {
            viewState.showLoading(true)
            viewState.setClientsSpinner(_dao.getClients())
            viewState.showLoading(false)
        }
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
                if (_dao.update(
                    _old!!,
                    Record(
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

    fun processAddingNewClient() {
        loadClientsData()
        viewState.showClientAddedMessage()
    }

    fun passRecord(record: Record) {
        _old = record
    }
}
