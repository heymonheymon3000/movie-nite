package com.android.movie.nite.features.movieDetail.viewModels

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.android.movie.nite.features.movie.respository.MoviesRepository
import com.android.movie.nite.utils.Constants
import kotlinx.coroutines.launch

class MovieDetailViewModel @ViewModelInject constructor(
    application: Application,
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle)
    : AndroidViewModel(application) {
    val movie =
        moviesRepository.movie(savedStateHandle.get<Int>("movie_id")!!)

    val movielist = moviesRepository.movies

    init {
        if(Constants.isNetworkConnected) {
            viewModelScope.launch {
                moviesRepository.refreshMovies()
            }
        }
    }
}