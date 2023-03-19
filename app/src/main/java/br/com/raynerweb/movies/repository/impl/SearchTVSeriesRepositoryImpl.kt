package br.com.raynerweb.movies.repository.impl

import br.com.raynerweb.movies.exception.HttpErrorException
import br.com.raynerweb.movies.ext.urlImage
import br.com.raynerweb.movies.repository.SearchTVSeriesRepository
import br.com.raynerweb.movies.repository.service.TVSeriesService
import br.com.raynerweb.movies.ui.model.TVShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchTVSeriesRepositoryImpl @Inject constructor(
    private val service: TVSeriesService
) : SearchTVSeriesRepository {
    override suspend fun fetchPopular(): List<TVShow> {
        return withContext(context = Dispatchers.IO) {
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
    }

    override suspend fun fetchByFilter(filter: String): List<TVShow> {
        return withContext(context = Dispatchers.IO) {
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
    }


}