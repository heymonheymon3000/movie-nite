package com.android.movie.nite.features.movieDetail.viewModels

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.android.movie.nite.features.movie.domain.Movie
import com.android.movie.nite.features.movie.respository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MovieDetailViewModel @ViewModelInject constructor(
    application: Application,
    private var moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val  _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val  _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    private val  _stars = MutableLiveData<Int>()
    val stars: LiveData<Int>
        get() = _stars

    init {
        getMovie(savedStateHandle.get<Int>("movie_id")!!)
        getSimilarMovies(savedStateHandle.get<Int>("movie_id")!!)
    }

    private fun getMovie(movieId: Int) {
        viewModelScope.launch {
            _movie.value = moviesRepository.getMovie(movieId)
            _stars.value  = _movie.value!!.vote_average.toInt()?.div(2)
        }
    }

    private fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            _movieList.value = moviesRepository.getSimilarMovies(movieId)
        }
    }
}