//package com.android.movie.nite.features.favoriteMovie.ui.adaptor
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.android.movie.nite.databinding.ListItemMovieBinding
//import com.android.movie.nite.features.movie.domain.Movie
//import com.android.movie.nite.features.movie.ui.adapter.MovieClick
//
//class FavoriteMovieAdapter(private var callback: MovieClick) :
//    ListAdapter<Movie, FavoriteMovieAdapter.FavoriteMovieViewHolder>(DiffCallback) {
//
//    class FavoriteMovieViewHolder(var binding: ListItemMovieBinding):
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(movie: Movie, callback: MovieClick) {
//            binding.movie = movie
//            binding.movieCallback = callback
//            // This is important, because it forces the data binding to execute immediately,
//            // which allows the RecyclerView to make the correct view size measurements
//            binding.executePendingBindings()
//        }
//    }
//
//    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
//        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
//        return FavoriteMovieViewHolder(ListItemMovieBinding.inflate(LayoutInflater.from(parent.context)))
//    }
//
//    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
//        holder.bind(getItem(position)!!, callback)
//    }
//}
//
