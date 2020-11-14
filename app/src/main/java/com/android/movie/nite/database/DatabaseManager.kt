package com.android.movie.nite.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.movie.nite.features.movie.domain.Movie

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

@Database(entities = [DatabaseMovie::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}
