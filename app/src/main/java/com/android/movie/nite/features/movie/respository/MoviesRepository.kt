package com.android.movie.nite.features.movie.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.android.movie.nite.BuildConfig
import com.android.movie.nite.database.DatabaseFavoriteMovie
import com.android.movie.nite.database.MoviesDatabase
import com.android.movie.nite.database.asDomainModel
import com.android.movie.nite.features.movie.domain.Movie
import com.android.movie.nite.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val database: MoviesDatabase,
                                           private val networkProvider: Retrofit) {

    val movies: LiveData<List<Movie>> =
        Transformations.map(database.movieDao.getMovies()) {
        it.asDomainModel()
        }

    val favoriteMovies: LiveData<List<Movie>> =
        Transformations.map(database.favoriteMovieDao.getMovies()) {
            it.asDomainModel()
        }

    suspend fun refreshMovies() = withContext(Dispatchers.IO) {
        val network = networkProvider.create(MovieService::class.java)
        val movies  = network.getMoviesAsync(BuildConfig.MOVIE_API_KEY).await()
        database.movieDao.insertAll(NetworkMovieContainer(movies.results).asDatabaseModel())
    }

    suspend fun getMovie(movieId: Int) : Movie = withContext(Dispatchers.IO) {
        val network = networkProvider.create(MovieService::class.java)
        val movie  = network.getMovieAsync(movieId, BuildConfig.MOVIE_API_KEY).await()
        return@withContext NetworkMovieXContainer(NetworkMovie(
            movie.id, movie.title, movie.vote_average, movie.poster_path,
            movie.backdrop_path, movie.overview, movie.adult, movie.release_date, movie.tagline
        )).asDomainModel()
    }

    suspend fun exists(movieId: Int) : Boolean = withContext(Dispatchers.IO) {
        return@withContext database.favoriteMovieDao.exists(movieId)
    }

    suspend fun  addFavoriteMovie(movie: Movie) = withContext(Dispatchers.IO) {
        Timber.i("Inserting ${movie.title}")
        database.favoriteMovieDao.insert(
            DatabaseFavoriteMovie(
                id = movie.id,
                title = movie.title,
                vote_average = movie.vote_average,
                poster_path = movie.poster_path,
                backdrop_path = movie.backdrop_path,
                overview = movie.overview,
                adult = movie.adult,
                release_date = movie.release_date,
                tagline = movie.tagline
            )
        )
    }

    suspend fun  removeFavoriteMovie(id: Int) = withContext(Dispatchers.IO) {
        database.favoriteMovieDao.delete(id)
    }

    suspend fun getSimilarMovies(movieId: Int) : List<Movie> = withContext(Dispatchers.IO) {
        val network = networkProvider.create(MovieService::class.java)
        val listResult = network.getSimilarMoviesAsync(movieId, BuildConfig.MOVIE_API_KEY).await()
        return@withContext NetworkMovieContainer(listResult.results).asDomainModel()
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        database.movieDao.deleteAll()
    }
}

