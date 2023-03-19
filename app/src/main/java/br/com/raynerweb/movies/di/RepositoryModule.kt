package br.com.raynerweb.movies.di

import br.com.raynerweb.movies.repository.SearchTVSeriesRepository
import br.com.raynerweb.movies.repository.impl.SearchTVSeriesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provide(repository: SearchTVSeriesRepositoryImpl): SearchTVSeriesRepository

}