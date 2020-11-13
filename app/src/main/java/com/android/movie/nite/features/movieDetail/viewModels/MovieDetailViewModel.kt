package com.android.movie.nite.features.movieDetail.viewModels

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.android.movie.nite.features.movie.respository.MoviesRepository

class MovieDetailViewModel @ViewModelInject constructor(
    application: Application,
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle)
    : AndroidViewModel(application) {
    val movie =
        moviesRepository.movie(savedStateHandle.get<Int>("movie_id")!!)

    val movielist = moviesRepository.movies

    val stars: Int?
        get() = movie.value?.vote_average?.toInt()?.div(2)
}