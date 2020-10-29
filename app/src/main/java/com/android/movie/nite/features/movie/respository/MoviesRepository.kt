package com.android.movie.nite.features.movie.respository

import android.graphics.Color
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.android.movie.nite.BuildConfig
import com.android.movie.nite.database.MoviesDatabase
import com.android.movie.nite.database.asDomainModel
import com.android.movie.nite.features.movie.domain.Movie
import com.android.movie.nite.network.Network
import com.android.movie.nite.network.NetworkMovieContainer
import com.android.movie.nite.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching movies from the network and storing on disk
 */
class MoviesRepository(private val database: MoviesDatabase) {
    val movies: LiveData<List<Movie>> = Transformations.map(database.movieDao.getMovies()) {
        it.asDomainModel()
    }

    suspend fun refreshMovies() = withContext(Dispatchers.IO) {
        // Check network connection
        val movies  = Network.movies.getMoviesAsync(BuildConfig.MOVIE_API_KEY).await()
        val netMovies = NetworkMovieContainer(movies.results)
        database.movieDao.insertAll(netMovies.asDatabaseModel())
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        database.movieDao.deleteAll()
    }
}

