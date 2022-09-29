package com.omarelrayes.moviedb.features.list.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.omarelrayes.moviedb.R
import com.omarelrayes.moviedb.core.utils.binding.viewBinding
import com.omarelrayes.moviedb.databinding.FragmentMovieListBinding
import com.omarelrayes.moviedb.features.list.domain.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {
    private val binding: FragmentMovieListBinding by viewBinding()
    private val viewModel: MovieListViewModel by viewModels()
    private val adapter by lazy {
        MoviesAdapter {
            onMovieClick(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        initSortingOptions()
        observeData()
    }

    private fun initAdapters() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItemViewType(position) == MoviesAdapter.LOADING_ITEM) {
                    1
                } else 2
            }
        }
        binding.moviesRecycler.layoutManager = gridLayoutManager
        binding.moviesRecycler.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter(::retry),
            footer = LoadingStateAdapter(::retry)
        )

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.prepend }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.moviesRecycler.scrollToPosition(0) }
        }
    }

    private fun retry() {
        adapter.retry()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest(adapter::submitData)
        }
    }

    private fun initSortingOptions() {
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.mostPopular -> {
                    viewModel.getMostPopularMovies()
                }
                R.id.topRated -> {
                    viewModel.getTopRatedMovies()
                }
            }
        }
    }

    private fun onMovieClick(movie: Movie) {
    }
}
