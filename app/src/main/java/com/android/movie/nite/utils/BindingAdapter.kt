package com.android.movie.nite.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.movie.nite.R
import com.android.movie.nite.features.movie.domain.Movie
import com.android.movie.nite.features.movie.ui.adapter.MovieAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
//        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        val imgUri = "https://image.tmdb.org/t/p/original$imgUrl"

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