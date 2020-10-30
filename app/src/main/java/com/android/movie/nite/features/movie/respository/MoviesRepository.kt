package com.android.movie.nite.features.movie.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.android.movie.nite.BuildConfig
import com.android.movie.nite.database.MoviesDatabase
import com.android.movie.nite.database.asDomainModel
import com.android.movie.nite.features.movie.domain.Movie
import com.android.movie.nite.network.MovieService
import com.android.movie.nite.network.NetworkMovieContainer
import com.android.movie.nite.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val database: MoviesDatabase,
                                           private val networkProvider: Retrofit) {

    val movies: LiveData<List<Movie>> =
        Transformations.map(database.movieDao.getMovies()) {
        it.asDomainModel()
    }

    suspend fun refreshMovies() = withContext(Dispatchers.IO) {
        val network = networkProvider.create(MovieService::class.java)
        val movies  = network.getMoviesAsync(BuildConfig.MOVIE_API_KEY).await()
        val netMovies = NetworkMovieContainer(movies.results)
        database.movieDao.insertAll(netMovies.asDatabaseModel())
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        database.movieDao.deleteAll()
    }
}

