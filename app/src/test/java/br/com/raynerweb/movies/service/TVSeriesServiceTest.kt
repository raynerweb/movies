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
    }

    @Test
    fun `Fetch by filter`() {
        val mockResponse = mockSuccessfulResponse("/json/response_fetch_by_filter.json")
        mockWebServer.enqueue(mockResponse)

        val filtered = service.fetchByFilter("filter").execute().body()

        assertNotNull(filtered)
    }
}