package com.omarelrayes.moviedb.features.list.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.omarelrayes.moviedb.features.list.domain.Movie
import com.omarelrayes.moviedb.features.list.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val service: MoviesService
) : MoviesRepository {

    override fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { MoviesPagingSource(service) }
        ).flow
    }

    override fun getMostPopularMovies() {
        MoviesPagingSource.changeSorting(SortBy.MostPopular)
    }

    override fun getTopRatedMovies() {
        MoviesPagingSource.changeSorting(SortBy.TopRated)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
