package com.android.movie.nite.features.movieDetail.ui

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.movie.nite.R
import com.android.movie.nite.databinding.FragmentMovieDetailBinding
import com.android.movie.nite.features.movie.ui.adapter.MovieClick
import com.android.movie.nite.features.movieDetail.adapter.MovieDetailAdapter
import com.android.movie.nite.features.movieDetail.viewModels.MovieDetailViewModel
import com.android.movie.nite.utils.calculateNoOfColumns
import com.android.movie.nite.utils.dpFromPx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_detail,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupRecyclerView()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                   findNavController().popBackStack()
                }
            }
        )

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        return binding.root
    }

    private fun setupRecyclerView() {
        val spanCount = calculateNoOfColumns(
            requireContext(),
            dpFromPx(requireContext(), resources.getDimension(R.dimen.default_poster_grid_width))
        )

        binding.rvSimilarMovies.itemAnimator = null;
        binding.rvSimilarMovies.layoutManager = GridLayoutManager(requireContext(), spanCount);
        binding.rvSimilarMovies.setHasFixedSize(true)
        (binding.rvSimilarMovies.layoutManager as GridLayoutManager).spanSizeLookup = object :
            GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) =  when (position) {
                0 -> spanCount
                else -> 1
            }
        }
        binding.rvSimilarMovies.addItemDecoration(object : RecyclerView.ItemDecoration() {
            val spacing = resources.getDimensionPixelSize(R.dimen.default_poster_grid_spacing) / 2

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State) {

                // here we assume that position 0 is the header
                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.set(spacing, spacing, spacing, spacing)
                }
            }
        })

        binding.rvSimilarMovies.adapter = MovieDetailAdapter(this, viewModel, MovieClick {
            findNavController().navigate(
                MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieDetailFragment(it.id, it.title))
        })
    }
}