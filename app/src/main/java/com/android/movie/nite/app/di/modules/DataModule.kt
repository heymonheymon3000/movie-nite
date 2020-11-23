package com.android.movie.nite.app.di.modules

import android.content.Context
import androidx.room.Room
import com.android.movie.nite.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideMovieDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context.applicationContext,
            MoviesDatabase::class.java,
            "mt-101").build()
}