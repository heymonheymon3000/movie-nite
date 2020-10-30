package com.android.movie.nite.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("popular")
    fun getMoviesAsync(@Query("api_key") api_key: String?): Deferred<NetworkResponseObject>
}

