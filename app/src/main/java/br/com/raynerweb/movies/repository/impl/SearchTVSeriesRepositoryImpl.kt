package br.com.raynerweb.movies.repository.impl

import br.com.raynerweb.movies.exception.HttpErrorException
import br.com.raynerweb.movies.ext.urlImage
import br.com.raynerweb.movies.repository.SearchTVSeriesRepository
import br.com.raynerweb.movies.repository.service.TVSeriesService
import br.com.raynerweb.movies.ui.model.Episode
import br.com.raynerweb.movies.ui.model.Season
import br.com.raynerweb.movies.ui.model.TVShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchTVSeriesRepositoryImpl @Inject constructor(
    private val service: TVSeriesService
) : SearchTVSeriesRepository {
    override suspend fun fetchPopular(): List<TVShow> = withContext(context = Dispatchers.IO) {
        val response = service.fetchPopular().execute()
        if (response.isSuccessful) {
            val results = response.body()?.results ?: emptyList()
            return@withContext results.map { show ->
                TVShow(
                    title = show.name,
                    firstAirDate = show.firstAirDate,
                    overview = show.overview,
                    poster = show.poster.urlImage(),
                    backdrop = show.backdrop.urlImage()
                )
            }
        }
        throw HttpErrorException()
    }

    override suspend fun fetchByFilter(filter: String): List<TVShow> =
        withContext(context = Dispatchers.IO) {
            val response = service.fetchByFilter(filter).execute()
            if (response.isSuccessful) {
                val results = response.body()?.results ?: emptyList()
                return@withContext results.map { show ->
                    TVShow(
                        title = show.name,
                        firstAirDate = show.first_air_date,
                        overview = show.overview,
                        poster = show.poster_path.urlImage(),
                        backdrop = show.backdrop_path.urlImage()
                    )
                }
            }
            throw HttpErrorException()
        }

    override suspend fun fetchSeasons(tvShowId: Int): List<Season> =
        withContext(context = Dispatchers.IO) {
            val response = service.fetchSeasons(tvShowId).execute()
            if (response.isSuccessful) {
                val results = response.body()?.seasons ?: emptyList()
                return@withContext results.map {
                    Season(
                        id = it.id,
                        airDate = it.air_date,
                        episodeCount = it.episode_count,
                        name = it.name,
                        overview = it.overview,
                        poster = it.poster_path.urlImage(),
                        seasonNumber = it.season_number
                    )
                }.sortedBy { it.seasonNumber }
            }
            throw HttpErrorException()
        }

    override suspend fun fetchEpisodes(tvShowId: Int, seasonId: Int): List<Episode> =
        withContext(context = Dispatchers.IO) {
            val response = service.fetchEpisodes(tvShowId, seasonId).execute()
            if (response.isSuccessful) {
                val results = response.body()?.episodes ?: emptyList()
                return@withContext results.map {
                    Episode(
                        id = it.id,
                        episodeNumber = it.episode_number,
                        name = it.name,
                        overview = it.overview,
                        picture = it.still_path.urlImage(),
                        airDate = it.air_date
                    )
                }.sortedBy { it.episodeNumber }
            }
            throw HttpErrorException()
        }

    override suspend fun fetchKeywords(tvShowId: Int): List<String> =
        withContext(context = Dispatchers.IO) {
            val response = service.fetchKeywords(tvShowId).execute()
            if (response.isSuccessful) {
                val results = response.body()?.results ?: emptyList()
                return@withContext results.map { it.name }
            }
            throw HttpErrorException()
        }
}