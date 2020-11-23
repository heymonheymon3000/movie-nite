package com.android.movie.nite.features.movie.viewmodels

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.android.movie.nite.features.movie.respository.MoviesRepository
import com.android.movie.nite.utils.Constants
import kotlinx.coroutines.*

class MovieViewModel @ViewModelInject constructor(
    application: Application,
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle)
    : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _showNoInternetSnackbar = MutableLiveData<Boolean>()
    val showNoInternetSnackbar: LiveData<Boolean>
        get() = _showNoInternetSnackbar

    val movielist = moviesRepository.movies

    val favoriteMovielist = moviesRepository.favoriteMovies

    init {
        if(Constants.isNetworkConnected) {
            viewModelScope.launch {
                if(moviesRepository.isEmpty()) {
                    moviesRepository.refreshMovies()
                }
            }
        } else {
            _showNoInternetSnackbar.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun showNoInternetSnackbarComplete() {
        _showNoInternetSnackbar.value = false
    }
}