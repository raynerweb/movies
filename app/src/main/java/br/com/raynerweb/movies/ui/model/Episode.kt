package br.com.raynerweb.movies.ui.model

data class Episode(
    val id: Int,
    val episodeNumber: Int,
    val name: String,
    val overview: String,
    val picture: String, //still_path
    val airDate: String,
)
