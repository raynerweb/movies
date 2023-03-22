package br.com.raynerweb.movies.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TVShow(
    val id: Int,
    val title: String,
    val firstAirDate: String,
    val overview: String,
    val poster: String,
    val backdrop: String
) : Parcelable
