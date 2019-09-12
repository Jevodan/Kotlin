package com.example.jevodan.union.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<String>()
    private var check = true;

    init {
        viewStateLiveData.value = "Hello World!"
    }

    fun viewState(): LiveData<String> = viewStateLiveData
    fun regClick() {
        check = !check
        viewStateLiveData.value = "Пока регистрация невозможна"
    }
}
