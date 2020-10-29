package com.android.movie.nite.features.movie.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.android.movie.nite.database.getDatabase
import com.android.movie.nite.features.movie.respository.MoviesRepository
import com.android.movie.nite.network.Variables
import kotlinx.coroutines.*

class MovieViewModel (application: Application): AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(application)
    private val moviesRepository = MoviesRepository(database)

    private val _showNoInternetSnackbar = MutableLiveData<Boolean>()
    val showNoInternetSnackbar: LiveData<Boolean>
        get() = _showNoInternetSnackbar

    init {
        if(Variables.isNetworkConnected) {
            viewModelScope.launch {
                moviesRepository.refreshMovies()
            }
        } else {
            _showNoInternetSnackbar.value = true
        }
    }

    val movielist = moviesRepository.movies


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun showNoInternetSnackbarComplete() {
        _showNoInternetSnackbar.value = false
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}