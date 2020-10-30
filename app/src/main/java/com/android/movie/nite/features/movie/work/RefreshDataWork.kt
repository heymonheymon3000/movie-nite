package com.android.movie.nite.features.movie.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.movie.nite.features.movie.respository.MoviesRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.HttpException

class RefreshDataWorker(
    private val appContext: Context,
    params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface MoviesRepositoryPoint {
        fun getMoviesRepository() : MoviesRepository
    }

    override suspend fun doWork(): Result {
        return try {
            val entryPoint =
                EntryPointAccessors.fromApplication(appContext, MoviesRepositoryPoint::class.java)
            val moviesRepository = entryPoint.getMoviesRepository()
            moviesRepository.refreshMovies()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "RefreshDataWorkerTask"
    }
}