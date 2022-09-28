package com.omarelrayes.moviedb.features.list.data

import com.omarelrayes.moviedb.features.list.domain.Movie
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    @SerialName("total_pages") val totalPages: Int
)
