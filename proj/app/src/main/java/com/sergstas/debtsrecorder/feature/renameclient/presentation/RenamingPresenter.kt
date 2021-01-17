package com.sergstas.debtsrecorder.feature.renameclient.presentation

import com.sergstas.debtsrecorder.domain.entity.Client
import com.sergstas.debtsrecorder.feature.renameclient.data.RenamingDao
import com.sergstas.debtsrecorder.feature.renameclient.data.RenamingDaoImpl
import com.sergstas.debtsrecorder.feature.renameclient.enums.RenamingValidationError
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class RenamingPresenter(private val _dao: RenamingDao, private val _old: String): MvpPresenter<RenamingView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setClient(Client.fromString(_old)!!)
    }

    fun validate(first: String, last: String) {
        presenterScope.launch {
            viewState.showLoading(true)
            val client = Client(first, last)
            when {
                first.isEmpty() -> viewState.showValidationError(RenamingValidationError.FIRST_NAME_IS_NULL)
                last.isEmpty() -> viewState.showValidationError(RenamingValidationError.LAST_NAME_IS_NULL)
                first.contains(' ') || last.contains(' ') -> viewState.showValidationError(RenamingValidationError.NAME_CONTAIN_SPACES)
                _dao.checkIsNameOccupied(client) -> viewState.showValidationError(RenamingValidationError.CLIENT_ALREADY_EXISTS)
                !_dao.renameClient(Client.fromString(_old)!!, client) -> viewState.showValidationError(RenamingValidationError.UNKNOWN_ERROR)
                else -> viewState.close()
            }
            viewState.showLoading(false)
        }
    }
}