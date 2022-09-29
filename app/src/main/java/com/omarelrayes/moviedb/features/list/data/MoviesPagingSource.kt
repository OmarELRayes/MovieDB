package com.omarelrayes.moviedb.features.list.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omarelrayes.moviedb.features.list.domain.Movie

class MoviesPagingSource(private val service: MoviesService) : PagingSource<Int, Movie>() {

    companion object {
        private var sortBy = SortBy.MostPopular

        fun changeSorting(sortBy: SortBy) {
            this.sortBy = sortBy
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        /* Try to find the page key of the closest page to anchorPosition, from
         either the prevKey or the nextKey, but you need to handle nullability
         here:
          * prevKey == null -> anchorPage is the first page.
          * nextKey == null -> anchorPage is the last page.
          * both prevKey and nextKey null -> anchorPage is the initial page, so
            just return null.*/
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = service.fetchMovies(page, sortBy.getValue())
            LoadResult.Page(
                data = response.body()?.results!!,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (page == response.body()?.totalPages) null else page.plus(1)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}

enum class SortBy {
    MostPopular, TopRated
}

fun SortBy.getValue(): String {
    return when (this) {
        SortBy.MostPopular -> "popularity.des"
        SortBy.TopRated -> "vote_average.desc"
    }
}
