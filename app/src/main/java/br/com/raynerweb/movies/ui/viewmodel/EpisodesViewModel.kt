package br.com.raynerweb.movies.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raynerweb.movies.core.SingleLiveEvent
import br.com.raynerweb.movies.exception.HttpErrorException
import br.com.raynerweb.movies.repository.SearchTVSeriesRepository
import br.com.raynerweb.movies.ui.model.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val searchTVSeriesRepository: SearchTVSeriesRepository
) : ViewModel() {

    private val _loading = SingleLiveEvent<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _emptyResult = SingleLiveEvent<Unit>()
    val emptyResult: LiveData<Unit> get() = _emptyResult

    private val _episodes = MutableLiveData<List<Episode>>()
    val episodes: LiveData<List<Episode>> get() = _episodes

    fun fetchEpisodes(tvShowId: Int, seasonNumber: Int) = viewModelScope.launch {
        try {
            _loading.postValue(true)
            val episodes = searchTVSeriesRepository.fetchEpisodes(tvShowId, seasonNumber)
            if (episodes.isEmpty()) {
                _emptyResult.call()
            } else {
                _episodes.postValue(episodes)
            }
            _loading.postValue(false)
        } catch (e: HttpErrorException) {
            _loading.postValue(false)
        }
    }

}