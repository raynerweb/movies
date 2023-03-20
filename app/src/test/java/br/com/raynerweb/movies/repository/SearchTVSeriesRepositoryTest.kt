package br.com.raynerweb.movies.repository

import br.com.raynerweb.movies.exception.HttpErrorException
import br.com.raynerweb.movies.repository.impl.SearchTVSeriesRepositoryImpl
import br.com.raynerweb.movies.repository.service.TVSeriesService
import br.com.raynerweb.movies.repository.service.dto.response.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import retrofit2.Response
import retrofit2.mock.Calls

class SearchTVSeriesRepositoryTest : BaseRepositoryTest() {

    private val service = mock<TVSeriesService>()
    private val repository = SearchTVSeriesRepositoryImpl(service)

    @Test
    fun `Fetch the list of most popular TV Series and Map Result to TVShow`() = runBlocking {
        whenever(service.fetchPopular())
            .thenReturn(
                Calls.response(
                    gson.fromJson(
                        readJson("/json/response_fetch_popular_tv_series.json"),
                        ResponseTVShow::class.java
                    )
                )
            )

        val fetchPopular = repository.fetchPopular()
        assertNotNull(fetchPopular)

        fetchPopular.forEach {
            assertNotNull(it.poster)
            assertNotNull(it.backdrop)
        }
    }

    @Test
    fun `Should throw HttpErrorException`() {
        whenever(service.fetchPopular()).thenReturn(
            Calls.response(
                Response.error(
                    500,
                    readJson("/json/response_error.json").toResponseBody()
                )
            )
        )

        assertThrows(HttpErrorException::class.java) {
            runBlocking {
                repository.fetchPopular()
            }
        }
    }

    @Test
    fun `Fetch TV Series by filter`() = runBlocking {
        whenever(service.fetchByFilter(anyString()))
            .thenReturn(
                Calls.response(
                    gson.fromJson(
                        readJson("/json/response_fetch_by_filter.json"),
                        ResponseTVShowByFilter::class.java
                    )
                )
            )

        val filtered = repository.fetchByFilter("filter")
        assertNotNull(filtered)

        filtered.forEach {
            assertNotNull(it.poster)
            assertNotNull(it.backdrop)
        }
    }

    @Test
    fun `Error when It try to fetch TV Series by filter`() {
        whenever(service.fetchByFilter(anyString())).thenReturn(
            Calls.response(
                Response.error(
                    500,
                    readJson("/json/response_error.json").toResponseBody()
                )
            )
        )

        assertThrows(HttpErrorException::class.java) {
            runBlocking {
                repository.fetchByFilter("filter")
            }
        }
    }

    @Test
    fun `Fetch TV Details with Seasons`() = runBlocking {
        whenever(service.fetchSeasons(anyInt()))
            .thenReturn(
                Calls.response(
                    gson.fromJson(
                        readJson("/json/response_seasons.json"),
                        ResponseSeasons::class.java
                    )
                )
            )

        val seasons = repository.fetchSeasons(10080)
        assertNotNull(seasons)
    }

    @Test
    fun `Fetch TV Details with Seasons and Ordered By Season Number`() = runBlocking {
        whenever(service.fetchSeasons(anyInt()))
            .thenReturn(
                Calls.response(
                    gson.fromJson(
                        readJson("/json/response_seasons.json"),
                        ResponseSeasons::class.java
                    )
                )
            )

        val seasons = repository.fetchSeasons(10080)
        assertTrue(seasons.first().seasonNumber < seasons.last().seasonNumber)
    }

    @Test
    fun `Error when It try to fetch TV Details with Seasons`() {
        whenever(service.fetchSeasons(anyInt())).thenReturn(
            Calls.response(
                Response.error(
                    500,
                    readJson("/json/response_error.json").toResponseBody()
                )
            )
        )

        assertThrows(HttpErrorException::class.java) {
            runBlocking {
                repository.fetchSeasons(10080)
            }
        }
    }

    @Test
    fun `Fetch Episodes`() = runBlocking {
        whenever(service.fetchEpisodes(anyInt(), anyInt()))
            .thenReturn(
                Calls.response(
                    gson.fromJson(
                        readJson("/json/response_episodes.json"),
                        ResponseEpisode::class.java
                    )
                )
            )

        val episodes = repository.fetchEpisodes(10080, 1)
        assertNotNull(episodes)
    }

    @Test
    fun `Error when It try to fetch Episodes`() {
        whenever(service.fetchEpisodes(anyInt(), anyInt())).thenReturn(
            Calls.response(
                Response.error(
                    500,
                    readJson("/json/response_error.json").toResponseBody()
                )
            )
        )

        assertThrows(HttpErrorException::class.java) {
            runBlocking {
                repository.fetchEpisodes(10080, 1)
            }
        }
    }

    @Test
    fun `Fetch Keywords`() = runBlocking {
        whenever(service.fetchKeywords(anyInt()))
            .thenReturn(
                Calls.response(
                    gson.fromJson(
                        readJson("/json/response_keywords.json"),
                        ResponseKeywords::class.java
                    )
                )
            )

        val keywords = repository.fetchKeywords(10080)
        assertNotNull(keywords)
        assertFalse(keywords.isEmpty())
    }

    @Test
    fun `Error when It try to Fetch Keywords`() {
        whenever(service.fetchKeywords(anyInt())).thenReturn(
            Calls.response(
                Response.error(
                    500,
                    readJson("/json/response_error.json").toResponseBody()
                )
            )
        )

        assertThrows(HttpErrorException::class.java) {
            runBlocking {
                repository.fetchKeywords(10080)
            }
        }
    }
}