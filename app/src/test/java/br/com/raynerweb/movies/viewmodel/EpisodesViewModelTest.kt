package br.com.raynerweb.movies.viewmodel

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.raynerweb.movies.repository.SearchTVSeriesRepository
import br.com.raynerweb.movies.test.CoroutineTestRule
import br.com.raynerweb.movies.ui.model.Episode
import br.com.raynerweb.movies.ui.viewmodel.EpisodesViewModel
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

class EpisodesViewModelTest {

    private val repository = mock<SearchTVSeriesRepository>()

    @InjectMocks
    lateinit var viewModel: EpisodesViewModel

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
    fun `Fetch the Episode's list`(): Unit =
        runBlocking {
            whenever(repository.fetchEpisodes(anyInt(), anyInt())).thenReturn(list())

            val episodesObserver = spy<Observer<List<Episode>>>()
            viewModel.episodes.observeForever(episodesObserver)

            val emptyResultObserver = spy<Observer<Unit>>()
            viewModel.emptyResult.observeForever(emptyResultObserver)

            val loadingObserver = spy<Observer<Boolean>>()
            viewModel.loading.observeForever(loadingObserver)

            viewModel.fetchEpisodes(1, 1)

            verify(episodesObserver).onChanged(eq(list()))
            verify(emptyResultObserver, never()).onChanged(anyOrNull())
            verify(loadingObserver, times(1)).onChanged(true)
            verify(loadingObserver, times(1)).onChanged(false)
        }

    @Test
    fun `Fetch the Season's list and receive a empty list`(): Unit =
        runBlocking {
            whenever(repository.fetchEpisodes(anyInt(), anyInt())).thenReturn(emptyList())

            val episodesObserver = spy<Observer<List<Episode>>>()
            viewModel.episodes.observeForever(episodesObserver)

            val emptyResultObserver = spy<Observer<Unit>>()
            viewModel.emptyResult.observeForever(emptyResultObserver)

            val loadingObserver = spy<Observer<Boolean>>()
            viewModel.loading.observeForever(loadingObserver)

            viewModel.fetchEpisodes(1, 1)

            verify(episodesObserver, never()).onChanged(emptyList())
            verify(emptyResultObserver, times(1)).onChanged(anyOrNull())
            verify(loadingObserver, times(1)).onChanged(true)
            verify(loadingObserver, times(1)).onChanged(false)
        }

    private fun list() =
        listOf(
            Episode(
                1, 1, "", "", "", ""
            )
        )

}