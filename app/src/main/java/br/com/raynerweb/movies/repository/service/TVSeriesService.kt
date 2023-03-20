package br.com.raynerweb.movies.repository.service

import br.com.raynerweb.movies.repository.service.dto.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVSeriesService {

    @GET("tv/popular")
    fun fetchPopular(): Call<ResponseTVShow>

    @GET("search/tv")
    fun fetchByFilter(@Query("query") query: String): Call<ResponseTVShowByFilter>

    @GET("tv/{id}")
    fun fetchSeasons(@Path("id") id: Int): Call<ResponseSeasons>

    @GET("tv/{id}/season/{season}")
    fun fetchEpisodes(@Path("id") id: Int, @Path("season") season: Int): Call<ResponseEpisode>

    @GET("tv/{id}/keywords")
    fun fetchKeywords(@Path("id") id: Int): Call<ResponseKeywords>

}