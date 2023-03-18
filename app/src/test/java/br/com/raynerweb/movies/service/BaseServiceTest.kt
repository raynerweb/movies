package br.com.raynerweb.movies.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raynerweb.movies.mock.MockNetworkModule
import br.com.raynerweb.movies.repository.service.interceptor.TokenInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseServiceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    protected val backend = MockNetworkModule()

    protected val retrofit = backend.provideRetrofit(
        okHttpClient = MockNetworkModule().provideOkHttpClient(
            tokenInterceptor = TokenInterceptor()
        ),
        converter = MockNetworkModule().provideConverterFactory(),
    )

    internal lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8000)
    }

    @After
    fun tearDown() = mockWebServer.shutdown()

    protected fun mockSuccessfulResponse(file: String): MockResponse = MockResponse()
        .setResponseCode(200)
        .setBody(readJson(file))

    protected fun mockUnsuccessfulResponse(): MockResponse = MockResponse()
        .setResponseCode(500)
        .setBody(readJson("/json/response_error.json"))

    private fun readJson(file: String): String =
        this::class.java.getResourceAsStream(file)?.readBytes()?.toString(Charsets.UTF_8)!!
}