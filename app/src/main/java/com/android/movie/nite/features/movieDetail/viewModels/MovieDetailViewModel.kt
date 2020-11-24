package com.android.movie.nite.features.movieDetail.viewModels

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.android.movie.nite.features.movie.domain.Movie
import com.android.movie.nite.features.movie.respository.MoviesRepository
import com.android.movie.nite.utils.Constants
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

    val movie = savedStateHandle.get<Int>(Constants.MOVIE_ID)?.let { moviesRepository.movie(it) }

    private val  _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    private val  _stars = MutableLiveData<Int>()
    val stars: LiveData<Int>
        get() = _stars

    private val  _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    init {
        storeMovie(savedStateHandle.get<Int>(Constants.MOVIE_ID)!!)
        getSimilarMovies(savedStateHandle.get<Int>(Constants.MOVIE_ID)!!)
    }

    fun onClickFavorite() {
        viewModelScope.launch {
            if(_isFavorite.value == false) {
                if (movie != null) {
                    movie.value?.let { moviesRepository.addFavoriteMovie(it) }
                }
                _isFavorite.value = true
            } else {
                moviesRepository.removeFavoriteMovie(savedStateHandle.get<Int>(Constants.MOVIE_ID)!!)
                _isFavorite.value = false
            }
        }
    }

    private fun storeMovie(movieId: Int) {
        viewModelScope.launch {
             moviesRepository.storeMovie(movieId)
            if (movie != null) {
                _stars.value = movie.value?.vote_average?.div(2)?.toInt()
                _isFavorite.value = moviesRepository.exists(movieId)
            }
        }
    }

    private fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            _movieList.value = moviesRepository.getSimilarMovies(movieId)
        }
    }
}