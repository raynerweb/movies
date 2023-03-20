package br.com.raynerweb.movies.repository.service.dto.response

import com.google.gson.annotations.SerializedName

data class ResponseSeasons(
    val seasons: List<Season>,
)

data class Season(
    val air_date: String,
    @SerializedName("episode_count")
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path")
    val poster_path: String,
    val season_number: Int
)