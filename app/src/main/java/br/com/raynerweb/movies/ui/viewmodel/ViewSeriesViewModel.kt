package br.com.raynerweb.movies.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raynerweb.movies.core.SingleLiveEvent
import br.com.raynerweb.movies.exception.HttpErrorException
import br.com.raynerweb.movies.repository.SearchTVSeriesRepository
import br.com.raynerweb.movies.ui.model.Season
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewSeriesViewModel @Inject constructor(
    private val searchTVSeriesRepository: SearchTVSeriesRepository
) : ViewModel() {

    private val _loading = SingleLiveEvent<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _emptyResult = SingleLiveEvent<Unit>()
    val emptyResult: LiveData<Unit> get() = _emptyResult

    private val _seasons = MutableLiveData<List<Season>>()
    val seasons: LiveData<List<Season>> get() = _seasons

    private val _keywords = SingleLiveEvent<List<String>>()
    val keywords: LiveData<List<String>> get() = _keywords

    private val _emptyKeywords = SingleLiveEvent<Unit>()
    val emptyKeywords: LiveData<Unit> get() = _emptyKeywords

    fun fetchSeasons(tvShowId: Int) = viewModelScope.launch {
        try {
            _loading.postValue(true)
            val fetchPopular = searchTVSeriesRepository.fetchSeasons(tvShowId)
            if (fetchPopular.isEmpty()) {
                _emptyResult.call()
            } else {
                _seasons.postValue(fetchPopular)
            }
            _loading.postValue(false)
        } catch (e: HttpErrorException) {
            _loading.postValue(false)
        }
    }

    fun fetchKeywords(tvShowId: Int) = viewModelScope.launch {
        try {
            _loading.postValue(true)
            val keywords = searchTVSeriesRepository.fetchKeywords(tvShowId)
            if (keywords.isEmpty()) {
                _emptyKeywords.call()
            } else {
                _keywords.postValue(keywords)
            }
            _loading.postValue(false)
        } catch (e: HttpErrorException) {
            _loading.postValue(false)
        }
    }
}