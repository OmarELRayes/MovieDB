package com.omarelrayes.moviedb.features.list.data

import androidx.paging.InvalidatingPagingSourceFactory
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

    private val pagingSource = InvalidatingPagingSourceFactory {
        MoviesPagingSource(service)
    }

    override fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { pagingSource.invoke() }
        ).flow
    }

    override fun getMostPopularMovies() {
        MoviesPagingSource.changeSorting(SortBy.MostPopular)
        pagingSource.invalidate()
    }

    override fun getTopRatedMovies() {
        MoviesPagingSource.changeSorting(SortBy.TopRated)
        pagingSource.invalidate()
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
