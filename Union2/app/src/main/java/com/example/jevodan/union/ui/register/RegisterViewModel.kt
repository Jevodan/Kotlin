package com.example.jevodan.union.ui.register


import com.example.jevodan.union.data.Repository
import com.example.jevodan.union.data.errors.NoAuthException
import com.example.jevodan.union.ui.base.BaseViewModel

class RegisterViewModel : BaseViewModel<Boolean?, RegisterViewState>() {

    fun requestUser() {
        Repository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                RegisterViewState(authenticated = true)
            } ?: let {
                RegisterViewState(error = NoAuthException())
            }
        }
    }

}
