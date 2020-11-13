package com.android.movie.nite.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.movie.nite.R
import com.android.movie.nite.features.movie.domain.Movie
import com.android.movie.nite.features.movie.ui.adapter.MovieAdapter
import com.android.movie.nite.features.movieDetail.adapter.MovieDetailAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.lang.StringBuilder

@BindingAdapter("listDataMovie")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataMovieDetail")
fun bindRecyclerView1(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieDetailAdapter
    adapter.addHeaderAndSubmitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = StringBuilder(Constants.IMAGE_URL)
            .append(imgUrl).toString()
            .toUri().buildUpon().scheme("https").build()

        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.NORMAL)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}