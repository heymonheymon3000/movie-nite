package com.android.movie.nite.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM databasemovie")
    fun getMovies(): LiveData<List<DatabaseMovie>>

    @Query("SELECT * FROM databasemovie WHERE id IN (:movieIds)")
    fun getSimilarMovies(movieIds: List<Int>): LiveData<List<DatabaseMovie>>

    @Query("SELECT * FROM databasemovie WHERE id = :id")
    fun getMovie(id: Int): LiveData<DatabaseMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<DatabaseMovie>)

    @Query("delete from databasemovie")
    suspend fun deleteAll()
}

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM databasefavoritemovie")
    fun getMovies(): LiveData<List<DatabaseFavoriteMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: DatabaseFavoriteMovie)

    @Query("delete from databasefavoritemovie WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT EXISTS (SELECT 1 FROM databasefavoritemovie WHERE id = :id)")
    fun exists(id: Int): Boolean
}

@Database(entities = [DatabaseMovie::class, DatabaseFavoriteMovie::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val favoriteMovieDao: FavoriteMovieDao
}
