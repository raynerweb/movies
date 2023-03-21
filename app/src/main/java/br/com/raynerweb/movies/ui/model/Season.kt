package br.com.raynerweb.movies.ui.model

data class Season(
    val id: Int,
    val episodeCount: Int,
    val name: String,
    val overview: String,
    val poster: String,
    val seasonNumber: Int
)