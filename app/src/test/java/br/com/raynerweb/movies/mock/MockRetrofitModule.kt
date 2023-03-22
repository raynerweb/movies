package br.com.raynerweb.movies.mock

import br.com.raynerweb.movies.di.NetworkModule
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class MockNetworkModule : NetworkModule() {

    override fun baseUrl() = "https://api.themoviedb.org/3/"

}