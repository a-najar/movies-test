package com.geniusforapp.movies.ui.ktx

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun ViewModel.launch(
    errorLiveData: MutableLiveData<Throwable>,
    loadingLiveData: MutableLiveData<Boolean>,
    block: suspend () -> Unit
) {
    try {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            block()
            loadingLiveData.postValue(false)
        }
    } catch (error: Throwable) {
        errorLiveData.postValue(error)
        loadingLiveData.postValue(false)
    }
}