package com.omarelrayes.moviedb.features.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.omarelrayes.moviedb.R
import com.omarelrayes.moviedb.core.api.ApiConstants
import com.omarelrayes.moviedb.core.utils.binding.viewBinding
import com.omarelrayes.moviedb.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val binding: FragmentMovieDetailsBinding by viewBinding()
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        with(binding) {
            viewModel.movie?.let {
                title.text = it.title
                overview.text = it.overview
                date.text = it.releaseDate
                rating.text = it.rating.toString()
                poster.load(ApiConstants.getFullImagePath(it.image)) {
                    error(R.drawable.ic_error)
                }
            }
        }
    }
}
