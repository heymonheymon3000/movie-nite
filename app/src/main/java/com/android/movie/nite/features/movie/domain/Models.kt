package com.android.movie.nite.features.movie.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val poster_path: String?,
    val overview: String,
    val adult: Boolean,
    val release_date: String
) : Parcelable