package com.android.movie.nite.features.movie.work

import android.content.Context

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.movie.nite.database.getDatabase
import com.android.movie.nite.features.movie.respository.MoviesRepository

import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    /**
     * A coroutine-friendly method to do your work.
     */
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = MoviesRepository(database)
        return try {
            repository.refreshMovies()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}