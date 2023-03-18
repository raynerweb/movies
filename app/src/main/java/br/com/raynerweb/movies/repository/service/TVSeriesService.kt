package br.com.raynerweb.movies.repository.service

import br.com.raynerweb.movies.repository.service.dto.response.ResponseTVShow
import retrofit2.Call
import retrofit2.http.GET

interface TVSeriesService {

    @GET("tv/popular")
    fun fetchPopular(): Call<ResponseTVShow>

}