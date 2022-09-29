package com.omarelrayes.moviedb.features.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.omarelrayes.moviedb.features.list.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    val movies = moviesRepository.getMovies()
        .cachedIn(viewModelScope)

    fun getMostPopularMovies() {
        moviesRepository.getMostPopularMovies()
    }

    fun getTopRatedMovies() {
        moviesRepository.getTopRatedMovies()
    }
}
