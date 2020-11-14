package com.android.movie.nite.features.movieDetail.viewModels

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.android.movie.nite.BuildConfig
import com.android.movie.nite.features.movie.domain.Movie
import com.android.movie.nite.features.movie.respository.MoviesRepository
import com.android.movie.nite.network.MovieService
import com.android.movie.nite.network.NetworkMovieContainer
import com.android.movie.nite.network.asDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import timber.log.Timber

class MovieDetailViewModel @ViewModelInject constructor(
    application: Application,
    private var moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle)
    : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        loadMovie(savedStateHandle.get<Int>("movie_id")!!)
        getSimilarMovies(savedStateHandle.get<Int>("movie_id")!!)
    }

    private val  _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val  _movielist = MutableLiveData<List<Movie>>()
    val movielist: LiveData<List<Movie>>
        get() = _movielist

    private val  _stars = MutableLiveData<Int>()
    val stars: LiveData<Int>
        get() = _stars

    private fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _movie.value = moviesRepository.loadMovie(movieId)
            _stars.value  = _movie.value!!.vote_average.toInt()?.div(2)
        }
    }

    private fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            _movielist.value = moviesRepository.getSimilarMovies(movieId)

        }
    }
}