//package com.android.movie.nite.features.favoriteMovie.viewmodels
//
//import android.app.Application
//import androidx.hilt.Assisted
//import androidx.hilt.lifecycle.ViewModelInject
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.SavedStateHandle
//import com.android.movie.nite.features.movie.respository.MoviesRepository
//import com.android.movie.nite.utils.Constants
//import kotlinx.coroutines.*
//
//class FavoriteMovieViewModel @ViewModelInject constructor(
//    application: Application,
//    private val moviesRepository: MoviesRepository,
//    @Assisted private val savedStateHandle: SavedStateHandle
//)
//    : AndroidViewModel(application) {
//
//    private val viewModelJob = SupervisorJob()
//    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
//
//    private val _showNoInternetSnackbar = MutableLiveData<Boolean>()
//    val showNoInternetSnackbar: LiveData<Boolean>
//        get() = _showNoInternetSnackbar
//
//    val movielist = moviesRepository.favoriteMovies
//
//    init {
//        if(!Constants.isNetworkConnected) {
//            _showNoInternetSnackbar.value = true
//        }
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        viewModelScope.cancel()
//    }
//
//    fun showNoInternetSnackbarComplete() {
//        _showNoInternetSnackbar.value = false
//    }
//}