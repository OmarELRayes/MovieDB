package com.omarelrayes.moviedb.features.list.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Movie(
    val id: Int,
    @SerialName("original_title") val title: String,
    @SerialName("poster_path") val image: String?,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val rating: Double,
    val overview: String
) : Parcelable
