package br.com.raynerweb.movies.service

import br.com.raynerweb.movies.repository.service.TVSeriesService
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class TVSeriesServiceTest : BaseServiceTest() {

    lateinit var service: TVSeriesService

    @Before
    fun setup() {
        service = networkModule.tvSeriesService(retrofit)
    }

    @Test
    fun `Fetch the list of most popular TV Series`() {
        val mockResponse = mockSuccessfulResponse("/json/response_fetch_popular_tv_series.json")
        mockWebServer.enqueue(mockResponse)

        val fetchPopular = service.fetchPopular().execute().body()

        assertNotNull(fetchPopular)
        assertNotNull(fetchPopular?.results)
    }

    @Test
    fun `Fetch by filter`() {
        val mockResponse = mockSuccessfulResponse("/json/response_fetch_by_filter.json")
        mockWebServer.enqueue(mockResponse)

        val response = service.fetchByFilter("filter").execute().body()

        assertNotNull(response)
        assertNotNull(response?.results)
    }

    @Test
    fun `Fetch TV Details`() {
        val mockResponse = mockSuccessfulResponse("/json/response_seasons.json")
        mockWebServer.enqueue(mockResponse)

        val response = service.fetchSeasons(100088).execute().body()

        assertNotNull(response)
        assertNotNull(response?.seasons)
    }

    @Test
    fun `Fetch Keywords`() {
        val mockResponse = mockSuccessfulResponse("/json/response_keywords.json")
        mockWebServer.enqueue(mockResponse)

        val response = service.fetchKeywords(100088).execute().body()

        assertNotNull(response)
        assertNotNull(response?.results)
    }

    @Test
    fun `Fetch Episodes`() {
        val mockResponse = mockSuccessfulResponse("/json/response_episodes.json")
        mockWebServer.enqueue(mockResponse)

        val response = service.fetchEpisodes(100088, 1).execute().body()

        assertNotNull(response)
        assertNotNull(response?.episodes)
    }
}