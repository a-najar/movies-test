package com.geniusforapp.movies.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geniusforapp.movies.domain.entities.Movie
import com.geniusforapp.movies.domain.usecases.getMovieUseCase
import com.geniusforapp.movies.ui.ktx.launch

class DetailsViewModel(
    private val movieId: Int,
    private val getMovieDetails: suspend (Int) -> Movie = { getMovieUseCase(it) }
) : ViewModel() {

    private val _errorsLiveData = MutableLiveData<Throwable>()
    val errorsLiveData: LiveData<Throwable>
        get() = _errorsLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    val movieDetails: LiveData<Movie> = MutableLiveData<Movie>().apply {
        launch(_errorsLiveData, _loadingLiveData) { postValue(getMovieDetails(movieId)) }
    }
}


class DetailsFactory(
    private val movieId: Int,
    private val getMovieDetails: suspend (Int) -> Movie = { getMovieUseCase(it) }
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(movieId, getMovieDetails) as T
    }

}