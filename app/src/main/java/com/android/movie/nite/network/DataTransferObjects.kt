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

@JsonClass(generateAdapter = true)
data class NetworkMovieContainer(val movies: List<NetworkMovie>)

@JsonClass(generateAdapter = true)
data class NetworkMovie(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val poster_path: String?,
    val overview: String = "",
    val adult: Boolean,
    val release_date: String = ""
)

fun NetworkMovieContainer.asDomainModel(): List<Movie> {
    return movies.map {
        Movie(
            id = it.id,
            title = it.title,
            vote_average = it.vote_average,
            poster_path = it.poster_path,
            overview = it.overview,
            adult = it.adult,
            release_date = it.release_date
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
            overview = it.overview,
            adult = it.adult,
            release_date = it.release_date
        )
    }
}







//@JsonClass(generateAdapter = true)
//data class com.android.movie.nite.network.NetworkMovieContainer(val movies: List<com.android.movie.nite.network.NetworkMovie>)
//
//@JsonClass(generateAdapter = true)
//data class com.android.movie.nite.network.NetworkMovie(
//    @Json(name = "id")
//    val id: Long,
//    @Json(name = "vote_count")
//    val voteCount: Long?,
//    @Json(name = "video")
//    val video: Boolean?,
//    @Json(name = "vote_average")
//    val voteAverage: Double?,
//    @Json(name = "title")
//    val title: String?,
//    @Json(name = "popularity")
//    val popularity: Double?,
//    @Json(name = "poster_path")
//    val posterPath: String?,
//    @Json(name = "original_language")
//    val originalLanguage: String?,
//    @Json(name = "original_title")
//    val originalTitle: String?,
//    @Json(name = "backdrop_path")
//    val backdropPath: String?,
//    @Json(name = "adult")
//    val adult: Boolean?,
//    @Json(name = "overview")
//    val overview: String?,
//    @Json(name = "release_date")
//    val releaseDate: String?)
//
//fun com.android.movie.nite.network.NetworkMovieContainer.com.android.movie.nite.network.asDomainModel(): List<Movie> {
//    return movies.map {
//        Movie(
//            id = it.id,
//            voteCount = it.voteCount,
//            video = it.video,
//            voteAverage = it.voteAverage,
//            title = it.title,
//            popularity = it.popularity,
//            posterPath = it.posterPath,
//            originalLanguage = it.originalLanguage,
//            originalTitle = it.originalTitle,
//            backdropPath = it.backdropPath,
//            adult = it.adult,
//            overview = it.overview,
//            releaseDate = it.releaseDate)
//    }
//}
//
//fun com.android.movie.nite.network.NetworkMovieContainer.com.android.movie.nite.network.asDatabaseModel(): Array<DatabaseMovie> {
//    return movies.map {
//        DatabaseMovie(
//            id = it.id,
//            voteCount = it.voteCount,
//            video = it.video,
//            voteAverage = it.voteAverage,
//            title = it.title,
//            popularity = it.popularity,
//            posterPath = it.posterPath,
//            originalLanguage = it.originalLanguage,
//            originalTitle = it.originalTitle,
//            backdropPath = it.backdropPath,
//            adult = it.adult,
//            overview = it.overview,
//            releaseDate = it.releaseDate)
//    }.toTypedArray()
//}
