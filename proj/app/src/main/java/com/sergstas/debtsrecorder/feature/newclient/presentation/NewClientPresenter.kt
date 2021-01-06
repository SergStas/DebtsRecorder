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
            val error = when {
                fn.isEmpty() -> ValidationError.FIRST_NAME_IS_NULL
                ln.isEmpty() -> ValidationError.LAST_NAME_IS_NULL
                fn.contains(' ') || ln.contains(' ') -> ValidationError.NAME_CONTAINS_SPACES
                _dao.doesClientExist(client) -> ValidationError.CLIENT_ALREADY_EXISTS
                else -> null
            }

            if (error != null)
                viewState.showValidationError(error)
            else if (_dao.addClient(client))
                viewState.close()
            else
                viewState.showValidationError(ValidationError.UNKNOWN)

            viewState.showLoading(false)
        }
    }
}