package com.omarelrayes.moviedb.features.list.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<PagingData<Movie>>
    fun getMostPopularMovies()
    fun getTopRatedMovies()
}
