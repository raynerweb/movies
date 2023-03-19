package br.com.raynerweb.movies.repository

import br.com.raynerweb.movies.exception.HttpErrorException
import br.com.raynerweb.movies.repository.impl.SearchTVSeriesRepositoryImpl
import br.com.raynerweb.movies.repository.service.TVSeriesService
import br.com.raynerweb.movies.repository.service.dto.response.ResponseTVShow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertThrows
import org.junit.Test
import retrofit2.Response
import retrofit2.mock.Calls

class SearchTVSeriesRepositoryTest : BaseRepositoryTest() {

    private val service = mock<TVSeriesService>()
    private val repository = SearchTVSeriesRepositoryImpl(service)

    @Test
    fun `Fetch the list of most popular TV Series and Transform to TVShow`() = runBlocking {
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
            assertNotNull(it.poster.startsWith("http"))
            assertNotNull(it.backdrop.startsWith("http"))
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
}