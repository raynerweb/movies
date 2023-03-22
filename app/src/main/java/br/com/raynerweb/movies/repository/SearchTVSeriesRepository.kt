package br.com.raynerweb.movies.repository

import br.com.raynerweb.movies.ui.model.Episode
import br.com.raynerweb.movies.ui.model.Season
import br.com.raynerweb.movies.ui.model.TVShow

interface SearchTVSeriesRepository {

    suspend fun fetchPopular(): List<TVShow>
    suspend fun fetchByFilter(filter: String): List<TVShow>
    suspend fun fetchSeasons(tvShowId: Int): List<Season>
    suspend fun fetchEpisodes(tvShowId: Int, seasonId: Int): List<Episode>
    suspend fun fetchKeywords(tvShowId: Int): List<String>

}