package br.com.raynerweb.movies.repository.service

import br.com.raynerweb.movies.repository.service.dto.response.ResponseTVShow
import br.com.raynerweb.movies.repository.service.dto.response.ResponseTVShowByFilter
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TVSeriesService {

    @GET("tv/popular")
    fun fetchPopular(): Call<ResponseTVShow>

    @GET("search/tv")
    fun fetchByFilter(@Query("query") query: String): Call<ResponseTVShowByFilter>
}