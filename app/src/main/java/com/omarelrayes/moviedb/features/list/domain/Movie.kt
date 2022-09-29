package com.omarelrayes.moviedb.features.list.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    @SerialName("original_title") val title: String,
    @SerialName("poster_path") val image: String?,
    val overview: String,
    @SerialName("release_date") val releaseDate: String
)
