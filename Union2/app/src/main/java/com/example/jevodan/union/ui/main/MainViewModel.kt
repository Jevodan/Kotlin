package com.example.jevodan.union

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.jevodan.union.data.Repository
import com.example.jevodan.union.ui.main.MainViewState

class MainViewModel : ViewModel() {

    private val viewStateLiveData : MutableLiveData<MainViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = MainViewState(Repository.menuBasket)
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData

}
