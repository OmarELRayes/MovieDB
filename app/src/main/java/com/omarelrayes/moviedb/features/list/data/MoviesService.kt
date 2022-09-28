package com.omarelrayes.moviedb.features.list.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("/3/discover/movie")
    suspend fun fetchMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String
    ): Response<MoviesResponse>
}
