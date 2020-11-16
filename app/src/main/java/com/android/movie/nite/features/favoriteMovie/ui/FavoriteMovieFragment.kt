package com.android.movie.nite.features.favoriteMovie.ui

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.movie.nite.R
import com.android.movie.nite.databinding.FragmentFavoriteMovieBinding
import com.android.movie.nite.features.movie.ui.adapter.MovieAdapter
import com.android.movie.nite.features.movie.ui.adapter.MovieClick
import com.android.movie.nite.features.movie.viewmodels.MovieViewModel
import com.android.movie.nite.utils.calculateNoOfColumns
import com.android.movie.nite.utils.dpFromPx
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMovieFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteMovieBinding
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorite_movie,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.showNoInternetSnackbar.observe(viewLifecycleOwner) { showMessage ->
            if (showMessage) {
                Snackbar.make(binding.root, "Please connect to internet", Snackbar.LENGTH_LONG)
                    .show()
                viewModel.showNoInternetSnackbarComplete()
            }
        }

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        val spanCount = calculateNoOfColumns(
            requireContext(),
            dpFromPx(requireContext(), resources.getDimension(R.dimen.default_poster_grid_width))
        )

        binding.rvFavoriteMovies.itemAnimator = null;
        binding.rvFavoriteMovies.layoutManager = GridLayoutManager(requireContext(), spanCount);
        binding.rvFavoriteMovies.setHasFixedSize(true)
        binding.rvFavoriteMovies.addItemDecoration(object : RecyclerView.ItemDecoration() {
            val spacing = resources.getDimensionPixelSize(R.dimen.default_poster_grid_spacing) / 2

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(spacing, spacing, spacing, spacing)
            }
        })

        binding.rvFavoriteMovies.adapter = MovieAdapter(MovieClick {
            findNavController().navigate(
                FavoriteMovieFragmentDirections.actionFavoriteMovieFragmentToMovieDetailFragment(it.id, it.title))
        })
    }
}