package com.android.movie.nite.network

import com.android.movie.nite.database.DatabaseMovie
import com.android.movie.nite.features.movie.domain.Movie
import com.squareup.moshi.JsonClass

data class NetworkResponseObject(
    val page: Int,
    val results: List<NetworkMovie>,
    val total_results: Int,
    val total_pages: Int
)

data class NetworkMovieResponseObject(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val poster_path: String?,
    val backdrop_path: String?,
    val overview: String = "",
    val adult: Boolean,
    val release_date: String = "",
    val tagline: String = ""
)
@JsonClass(generateAdapter = true)
data class NetworkMovieContainer(val movies: List<NetworkMovie>)

@JsonClass(generateAdapter = true)
data class NetworkMovieXContainer(val movie: NetworkMovie)

@JsonClass(generateAdapter = true)
data class NetworkMovie(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val poster_path: String?,
    val backdrop_path: String?,
    val overview: String = "",
    val adult: Boolean,
    val release_date: String = "",
    val tagline: String = ""
)

fun NetworkMovieXContainer.asDomainModel(): Movie {
    return Movie(
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
}

fun NetworkMovieContainer.asDomainModel(): List<Movie> {
    return movies.map {
        Movie(
            id = it.id,
            title = it.title,
            vote_average = it.vote_average,
            poster_path = it.poster_path,
            backdrop_path = it.backdrop_path,
            overview = it.overview,
            adult = it.adult,
            release_date = it.release_date,
            tagline = it.tagline
        )
    }
}

fun NetworkMovieContainer.asDatabaseModel(): List<DatabaseMovie> {
    return movies.map {
        DatabaseMovie(
            id = it.id,
            title = it.title,
            vote_average = it.vote_average,
            poster_path = it.poster_path,
            backdrop_path = it.backdrop_path,
            overview = it.overview,
            adult = it.adult,
            release_date = it.release_date,
            tagline = it.tagline
        )
    }
}

fun NetworkMovieXContainer.asDatabaseModel(): DatabaseMovie {
    return DatabaseMovie(
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
}