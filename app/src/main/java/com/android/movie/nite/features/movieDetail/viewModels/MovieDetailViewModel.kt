package com.android.movie.nite.features.movieDetail.viewModels

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.android.movie.nite.features.movie.respository.MoviesRepository

class MovieDetailViewModel @ViewModelInject constructor(
    application: Application,
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle)
    : AndroidViewModel(application) {

    val movielist = moviesRepository.movies

    val movie =
        moviesRepository.movie(savedStateHandle.get<Int>("movie_id")!!)
}