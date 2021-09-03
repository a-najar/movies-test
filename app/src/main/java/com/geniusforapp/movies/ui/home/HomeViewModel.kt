package com.geniusforapp.movies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geniusforapp.movies.domain.entities.Movies.Movie
import com.geniusforapp.movies.domain.usecases.*
import com.geniusforapp.movies.ui.ktx.launch

class HomeViewModel(
    private val getMoviesItems: MoviesUseCase = { getMoviesUseCase() },
) : ViewModel() {
    private val _loadingLiveData = MutableLiveData<Boolean>()
    private val _errorsLiveData = MutableLiveData<Throwable>()
    val errorsLiveData: LiveData<Throwable>
        get() = _errorsLiveData

    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    val moviesItems = MutableLiveData<List<MoviesItems>>().apply {
        launch(_errorsLiveData, _loadingLiveData) { postValue(getMoviesItems()) }
    }
}



