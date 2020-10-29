package com.android.movie.nite.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("popular")
    fun getMoviesAsync(@Query("api_key") api_key: String?): Deferred<NetworkResponseObject>
}

//private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//private val httpClient = OkHttpClient.Builder().addInterceptor(logging)


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/movie/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
//        .client(httpClient.build())
        .build()

    val movies = retrofit.create(MovieService::class.java)
}

class Variables {
    companion object {
        var isNetworkConnected : Boolean = false
    }
}
