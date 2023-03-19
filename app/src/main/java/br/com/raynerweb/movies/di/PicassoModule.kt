package br.com.raynerweb.movies.di

import android.content.Context
import com.squareup.picasso.BuildConfig
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class PicassoModule {

    @Provides
    @Singleton
    fun providePicasso(context: Context, okHttpClient: OkHttpClient): Picasso =
        Picasso.Builder(context)
            .indicatorsEnabled(BuildConfig.DEBUG)
            .downloader(OkHttp3Downloader(okHttpClient))
            .build()
}