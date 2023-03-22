package br.com.raynerweb.movies.viewmodel

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.raynerweb.movies.repository.SearchTVSeriesRepository
import br.com.raynerweb.movies.test.CoroutineTestRule
import br.com.raynerweb.movies.ui.model.Season
import br.com.raynerweb.movies.ui.viewmodel.ViewSeriesViewModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

class ViewSeriesViewModelTest {

    private val repository = mock<SearchTVSeriesRepository>()

    @InjectMocks
    lateinit var viewModel: ViewSeriesViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @CallSuper
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Fetch the Season's list`(): Unit =
        runBlocking {
            whenever(repository.fetchSeasons(anyInt())).thenReturn(list())

            val seasonsObserver = spy<Observer<List<Season>>>()
            viewModel.seasons.observeForever(seasonsObserver)

            val emptyResultObserver = spy<Observer<Unit>>()
            viewModel.emptyResult.observeForever(emptyResultObserver)

            val loadingObserver = spy<Observer<Boolean>>()
            viewModel.loading.observeForever(loadingObserver)

            viewModel.fetchSeasons(1)

            verify(seasonsObserver).onChanged(eq(list()))
            verify(emptyResultObserver, never()).onChanged(anyOrNull())
            verify(loadingObserver, times(1)).onChanged(true)
            verify(loadingObserver, times(1)).onChanged(false)
        }

    @Test
    fun `Fetch the Season's list and receive a empty list`(): Unit =
        runBlocking {
            whenever(repository.fetchSeasons(anyInt())).thenReturn(emptyList())

            val seasonsObserver = spy<Observer<List<Season>>>()
            viewModel.seasons.observeForever(seasonsObserver)

            val emptyResultObserver = spy<Observer<Unit>>()
            viewModel.emptyResult.observeForever(emptyResultObserver)

            val loadingObserver = spy<Observer<Boolean>>()
            viewModel.loading.observeForever(loadingObserver)

            viewModel.fetchSeasons(1)

            verify(seasonsObserver, never()).onChanged(emptyList())
            verify(emptyResultObserver, times(1)).onChanged(anyOrNull())
            verify(loadingObserver, times(1)).onChanged(true)
            verify(loadingObserver, times(1)).onChanged(false)
        }

    @Test
    fun `Fetch the keywords`(): Unit =
        runBlocking {
            whenever(repository.fetchKeywords(anyInt())).thenReturn(listOf(""))

            val keywordsObserver = spy<Observer<List<String>>>()
            viewModel.keywords.observeForever(keywordsObserver)

            val emptyKeywordsObserver = spy<Observer<Unit>>()
            viewModel.emptyKeywords.observeForever(emptyKeywordsObserver)

            val loadingObserver = spy<Observer<Boolean>>()
            viewModel.loading.observeForever(loadingObserver)

            viewModel.fetchKeywords(1)

            verify(keywordsObserver).onChanged(eq(listOf("")))
            verify(emptyKeywordsObserver, never()).onChanged(anyOrNull())
            verify(loadingObserver, times(1)).onChanged(true)
            verify(loadingObserver, times(1)).onChanged(false)
        }

    @Test
    fun `No keywords found`(): Unit =
        runBlocking {
            whenever(repository.fetchKeywords(anyInt())).thenReturn(emptyList())

            val keywordsObserver = spy<Observer<List<String>>>()
            viewModel.keywords.observeForever(keywordsObserver)

            val emptyKeywordsObserver = spy<Observer<Unit>>()
            viewModel.emptyKeywords.observeForever(emptyKeywordsObserver)

            val loadingObserver = spy<Observer<Boolean>>()
            viewModel.loading.observeForever(loadingObserver)

            viewModel.fetchKeywords(1)

            verify(keywordsObserver, never()).onChanged(anyOrNull())
            verify(emptyKeywordsObserver).onChanged(anyOrNull())
            verify(loadingObserver, times(1)).onChanged(true)
            verify(loadingObserver, times(1)).onChanged(false)
        }

    private fun list() =
        listOf(Season(0, 1, 0, "", "", "", 1))

}