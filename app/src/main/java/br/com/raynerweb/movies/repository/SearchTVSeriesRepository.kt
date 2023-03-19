package br.com.raynerweb.movies.repository

import br.com.raynerweb.movies.ui.model.TVShow

interface SearchTVSeriesRepository {

    suspend fun fetchPopular(): List<TVShow>

}