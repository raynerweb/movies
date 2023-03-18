package br.com.raynerweb.movies.di

import br.com.raynerweb.movies.BuildConfig
import br.com.raynerweb.movies.repository.service.TVSeriesService
import br.com.raynerweb.movies.repository.service.interceptor.TokenInterceptor
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {

    protected open fun baseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun tvSeriesService(retrofit: Retrofit) = retrofit.create(TVSeriesService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        converter: Converter.Factory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(converter)
            .client(okHttpClient)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            hostnameVerifier { _, _ -> true }
            addInterceptor(tokenInterceptor)

            addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
        }.build()


    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        val builder = GsonBuilder().apply {
            registerTypeAdapter(Date::class.java, JsonDeserializer { json, _, _ ->
                Date(json.asLong)
            })
        }.create()
        return GsonConverterFactory.create(builder)
    }
}