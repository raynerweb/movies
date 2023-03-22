package br.com.raynerweb.movies.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Season(
    val id: Int,
    val tvShowId: Int,
    val episodeCount: Int,
    val name: String,
    val overview: String,
    val poster: String,
    val seasonNumber: Int
) : Parcelable