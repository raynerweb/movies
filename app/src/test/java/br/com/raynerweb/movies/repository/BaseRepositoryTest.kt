package br.com.raynerweb.movies.repository

import br.com.raynerweb.movies.mock.MockNetworkModule

abstract class BaseRepositoryTest {

    private val networkModule = MockNetworkModule()

    protected val gson = networkModule.provideGson()

    protected fun readJson(file: String): String =
        this::class.java.getResourceAsStream(file)?.readBytes()?.toString(Charsets.UTF_8)!!

}