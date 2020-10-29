package com.android.movie.nite.features.movie.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.android.movie.nite.R
import com.android.movie.nite.databinding.FragmentMovieBinding
import com.android.movie.nite.features.movie.ui.adapter.MovieAdapter
import com.android.movie.nite.features.movie.ui.adapter.MovieClick
import com.android.movie.nite.features.movie.viewmodels.MovieViewModel
import com.android.movie.nite.utils.calculateNoOfColumns
import com.android.movie.nite.utils.dpFromPx
import com.google.android.material.snackbar.Snackbar

class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding

    private val viewModel: MovieViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, MovieViewModel.Factory(activity.application)).get(MovieViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.showNoInternetSnackbar.observe(viewLifecycleOwner, { showMessage ->
            if(showMessage) {
                Snackbar.make(binding.root, "Please connect to internet", Snackbar.LENGTH_LONG).show()
                viewModel.showNoInternetSnackbarComplete()
            }
        })

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        val spanCount = calculateNoOfColumns(requireContext(),
            dpFromPx(requireContext(), resources.getDimension(R.dimen.default_poster_grid_width)))

        binding.rvMovies.itemAnimator = null;
        binding.rvMovies.layoutManager = GridLayoutManager(requireContext(), spanCount);
        binding.rvMovies.setHasFixedSize(true)
        binding.rvMovies.addItemDecoration(object : ItemDecoration() {
            val spacing = resources.getDimensionPixelSize(R.dimen.default_poster_grid_spacing) / 2

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State) {
                outRect.set(spacing, spacing, spacing, spacing)
            }
        })

        binding.rvMovies.adapter = MovieAdapter(MovieClick {
            // TODO add event listener
        })
    }
}
