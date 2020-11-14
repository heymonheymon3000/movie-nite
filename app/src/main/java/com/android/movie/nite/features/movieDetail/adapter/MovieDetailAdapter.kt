package com.android.movie.nite.features.movieDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.movie.nite.databinding.HeaderBinding
import com.android.movie.nite.databinding.ListItemMovieBinding
import com.android.movie.nite.features.movie.domain.Movie
import com.android.movie.nite.features.movie.ui.adapter.MovieClick
import com.android.movie.nite.features.movieDetail.viewModels.MovieDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class MovieDetailAdapter(private val lifecycleOwner: LifecycleOwner, private val movieDetailViewModel: MovieDetailViewModel,
                         val callback: MovieClick) : ListAdapter<DataItem,
        RecyclerView.ViewHolder>(MovieDetailDiffCallback()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Movie>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.MovieItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (holder) {
//            is ViewHolder -> {
//                val movieItem = getItem(position) as DataItem.MovieItem
//                holder.bind(movieItem.movie, callback )
//            }
//        }

        when (holder) {
            is ViewHolderHeader -> {
                holder.bind( lifecycleOwner, movieDetailViewModel)
            }
            is ViewHolder -> {
                val movieItem = getItem(position) as DataItem.MovieItem
                holder.bind(movieItem.movie, callback )
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> ViewHolderHeader.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.MovieItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolderHeader(private val binding: HeaderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(lifecycleOwner: LifecycleOwner, movieDetailViewModel: MovieDetailViewModel) {
            binding.lifecycleOwner = lifecycleOwner
            binding.viewModel = movieDetailViewModel
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolderHeader {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HeaderBinding.inflate(layoutInflater, parent, false)
                return ViewHolderHeader(binding)
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, callback: MovieClick) {
            binding.movie = movie
            binding.movieCallback = callback
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemMovieBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class MovieDetailDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    data class MovieItem(val movie: Movie): DataItem() {
        override val id: Int = movie.id
    }

    object Header: DataItem() {
        override val id = Int.MIN_VALUE
    }

    abstract val id: Int
}