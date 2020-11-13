package com.android.movie.nite.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.movie.nite.features.movie.domain.Movie
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class DatabaseMovie constructor(
    @PrimaryKey
    val id: Int,
    val title: String,
    val vote_average: Double,
    val poster_path: String?,
    val backdrop_path: String?,
    val overview: String,
    val adult: Boolean,
    val release_date: String
) : Parcelable

fun List<DatabaseMovie>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            vote_average = it.vote_average,
            poster_path = it.poster_path,
            backdrop_path = it.backdrop_path,
            overview = it.overview,
            adult = it.adult,
            release_date = it.release_date
        )
    }
}

fun DatabaseMovie.asDomainModel(): Movie {
    return Movie(
        id = id,
        title = title,
        vote_average = vote_average,
        poster_path = poster_path,
        backdrop_path = backdrop_path,
        overview = overview,
        adult = adult,
        release_date = release_date
    )
}



