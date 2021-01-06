package com.sergstas.debtsrecorder.feature.newclient.presentation

import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.feature.newclient.data.NewClientDaoImpl
import com.sergstas.debtsrecorder.feature.newrecord.enums.ValidationError
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class NewClientPresenter(private val _dao: NewClientDaoImpl): MvpPresenter<NewClientView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setListeners()
    }

    fun validate(fn: String, ln: String) {
        presenterScope.launch {
            viewState.showLoading(true)
            val client = Client(fn, ln)
            if (fn.isEmpty())
                viewState.showValidationError(ValidationError.FIRST_NAME_IS_NULL)
            else if (ln.isEmpty())
                viewState.showValidationError(ValidationError.LAST_NAME_IS_NULL)
            else if (_dao.doesClientExist(client))
                viewState.showValidationError(ValidationError.CLIENT_ALREADY_EXISTS)
            else {
                _dao.addClient(client)
                viewState.close()
            }
            viewState.showLoading(false)
        }
    }
}