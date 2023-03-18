package br.com.raynerweb.movies.repository.service.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


class TokenInterceptor constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalRequest: HttpUrl = original.url
        val url = originalRequest.newBuilder()
            .addQueryParameter(API_KEY, API_VALUE)
            .build()

        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        const val API_KEY = "api_key"
        const val API_VALUE = "39a3c712614c598a6d5ca7a7c35a3ab1"
    }
}