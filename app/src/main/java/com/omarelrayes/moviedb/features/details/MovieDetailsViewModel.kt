package com.omarelrayes.moviedb.features.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.omarelrayes.moviedb.features.list.domain.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val movie = savedStateHandle.get<Movie>("movie")
}
