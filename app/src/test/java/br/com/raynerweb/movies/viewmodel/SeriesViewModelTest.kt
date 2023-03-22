package br.com.raynerweb.movies.viewmodel

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.raynerweb.movies.repository.SearchTVSeriesRepository
import br.com.raynerweb.movies.test.CoroutineTestRule
import br.com.raynerweb.movies.ui.model.TVShow
import br.com.raynerweb.movies.ui.viewmodel.SeriesViewModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

class SeriesViewModelTest {

    private val repository = mock<SearchTVSeriesRepository>()

    @InjectMocks
    lateinit var viewModel: SeriesViewModel

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
    fun `Fetch the list of most popular TV Shows`(): Unit =
        runBlocking {
            whenever(repository.fetchPopular()).thenReturn(list())

            val tvSeriesObserver = spy<Observer<List<TVShow>>>()
            viewModel.tvSeries.observeForever(tvSeriesObserver)

            val highlightObserver = spy<Observer<TVShow>>()
            viewModel.highlight.observeForever(highlightObserver)

            val emptyResultObserver = spy<Observer<Unit>>()
            viewModel.emptyResult.observeForever(emptyResultObserver)

            val loadingObserver = spy<Observer<Boolean>>()
            viewModel.loading.observeForever(loadingObserver)

            viewModel.search()

            verify(tvSeriesObserver).onChanged(eq(list()))
            verify(emptyResultObserver, never()).onChanged(anyOrNull())
            verify(highlightObserver, times(1)).onChanged(list().first())
            verify(loadingObserver, times(1)).onChanged(true)
            verify(loadingObserver, times(1)).onChanged(false)
        }

    @Test
    fun `Fetch the list of most popular TV Shows but it get a empty list`(): Unit =
        runBlocking {
            whenever(repository.fetchPopular()).thenReturn(emptyList())

            val tvSeriesObserver = spy<Observer<List<TVShow>>>()
            viewModel.tvSeries.observeForever(tvSeriesObserver)

            val highlightObserver = spy<Observer<TVShow>>()
            viewModel.highlight.observeForever(highlightObserver)

            val emptyResultObserver = spy<Observer<Unit>>()
            viewModel.emptyResult.observeForever(emptyResultObserver)

            val loadingObserver = spy<Observer<Boolean>>()
            viewModel.loading.observeForever(loadingObserver)

            viewModel.search()

            verify(tvSeriesObserver, never()).onChanged(emptyList())
            verify(highlightObserver, never()).onChanged(anyOrNull())

            verify(emptyResultObserver, times(1)).onChanged(anyOrNull())
            verify(loadingObserver, times(1)).onChanged(true)
            verify(loadingObserver, times(1)).onChanged(false)
        }

    @Test
    fun `Fetch TV Series by filter`(): Unit = runBlocking {
        whenever(repository.fetchByFilter(anyString())).thenReturn(list())

        val tvSeriesObserver = spy<Observer<List<TVShow>>>()
        viewModel.tvSeries.observeForever(tvSeriesObserver)

        val highlightObserver = spy<Observer<TVShow>>()
        viewModel.highlight.observeForever(highlightObserver)

        val emptyResultObserver = spy<Observer<Unit>>()
        viewModel.emptyResult.observeForever(emptyResultObserver)

        val loadingObserver = spy<Observer<Boolean>>()
        viewModel.loading.observeForever(loadingObserver)

        viewModel.queryFilter.postValue("some filter")
        viewModel.search()

        verify(tvSeriesObserver).onChanged(eq(list()))
        verify(emptyResultObserver, never()).onChanged(anyOrNull())
        verify(highlightObserver, times(1)).onChanged(list().first())
        verify(loadingObserver, times(1)).onChanged(true)
        verify(loadingObserver, times(1)).onChanged(false)
    }

    @Test
    fun `Fetch TV Series by filter and receive an empty list`(): Unit =
        runBlocking {
            whenever(repository.fetchByFilter(anyString())).thenReturn(emptyList())

            val tvSeriesObserver = spy<Observer<List<TVShow>>>()
            viewModel.tvSeries.observeForever(tvSeriesObserver)

            val highlightObserver = spy<Observer<TVShow>>()
            viewModel.highlight.observeForever(highlightObserver)

            val emptyResultObserver = spy<Observer<Unit>>()
            viewModel.emptyResult.observeForever(emptyResultObserver)

            val loadingObserver = spy<Observer<Boolean>>()
            viewModel.loading.observeForever(loadingObserver)

            viewModel.queryFilter.postValue("some filter")
            viewModel.search()

            verify(tvSeriesObserver, never()).onChanged(emptyList())
            verify(highlightObserver, never()).onChanged(anyOrNull())

            verify(emptyResultObserver, times(1)).onChanged(anyOrNull())
            verify(loadingObserver, times(1)).onChanged(true)
            verify(loadingObserver, times(1)).onChanged(false)
        }

    @Test
    fun `When the query filter is empty then need to fetch the popular series`() = runBlocking {
        whenever(repository.fetchPopular()).thenReturn(list())

        val tvSeriesObserver = spy<Observer<List<TVShow>>>()
        viewModel.tvSeries.observeForever(tvSeriesObserver)

        val highlightObserver = spy<Observer<TVShow>>()
        viewModel.highlight.observeForever(highlightObserver)

        val emptyResultObserver = spy<Observer<Unit>>()
        viewModel.emptyResult.observeForever(emptyResultObserver)

        val loadingObserver = spy<Observer<Boolean>>()
        viewModel.loading.observeForever(loadingObserver)

        viewModel.queryFilter.postValue("")
        viewModel.search()

        verify(tvSeriesObserver).onChanged(eq(list()))
        verify(emptyResultObserver, never()).onChanged(anyOrNull())
        verify(highlightObserver, times(1)).onChanged(list().first())
        verify(loadingObserver, times(1)).onChanged(true)
        verify(loadingObserver, times(1)).onChanged(false)
    }

    @Test
    fun `When the clear method is called then fetch the popular series`() = runBlocking {
        whenever(repository.fetchPopular()).thenReturn(list())

        val tvSeriesObserver = spy<Observer<List<TVShow>>>()
        viewModel.tvSeries.observeForever(tvSeriesObserver)

        val highlightObserver = spy<Observer<TVShow>>()
        viewModel.highlight.observeForever(highlightObserver)

        val emptyResultObserver = spy<Observer<Unit>>()
        viewModel.emptyResult.observeForever(emptyResultObserver)

        val loadingObserver = spy<Observer<Boolean>>()
        viewModel.loading.observeForever(loadingObserver)

        viewModel.clearFilter()

        verify(tvSeriesObserver).onChanged(eq(list()))
        verify(emptyResultObserver, never()).onChanged(anyOrNull())
        verify(highlightObserver, times(1)).onChanged(list().first())
        verify(loadingObserver, times(1)).onChanged(true)
        verify(loadingObserver, times(1)).onChanged(false)
    }

    private fun list() =
        listOf(TVShow(1, "", "", "", "", ""))

}