package br.com.raynerweb.movies.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raynerweb.movies.core.SingleLiveEvent
import br.com.raynerweb.movies.exception.HttpErrorException
import br.com.raynerweb.movies.repository.SearchTVSeriesRepository
import br.com.raynerweb.movies.ui.model.TVShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchSeriesViewModel @Inject constructor(
    private val searchTVSeriesRepository: SearchTVSeriesRepository
) : ViewModel() {

    private val _loading = SingleLiveEvent<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _emptyResult = SingleLiveEvent<Unit>()
    val emptyResult: LiveData<Unit> get() = _emptyResult

    private val _tvSeries = MutableLiveData<List<TVShow>>()
    val tvSeries: LiveData<List<TVShow>> get() = _tvSeries

    fun fetchPopular() = viewModelScope.launch {
        try {
            _loading.postValue(true)
            val fetchPopular = searchTVSeriesRepository.fetchPopular()
            if (fetchPopular.isEmpty()) {
                _emptyResult.call()
            } else {
                _tvSeries.postValue(fetchPopular)
            }
            _loading.postValue(false)
        } catch (e: HttpErrorException) {
            _loading.postValue(false)
        }

    }

}