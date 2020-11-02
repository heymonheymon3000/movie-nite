package com.android.movie.nite.utils

import java.util.*

class Constants {
    companion object {
        var isNetworkConnected : Boolean = false
        var workerId : UUID = UUID.randomUUID()
        val TOPIC : String = "movie_refresh"
        val IMAGE_URL : String = "image.tmdb.org/t/p/original"
    }
}
