package br.com.raynerweb.movies.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchSeriesViewModel @Inject constructor(): ViewModel() {

    private val _poster = MutableLiveData<Bitmap>()
    val poster: LiveData<Bitmap> get() = _poster

    private val _backdrop = MutableLiveData<Bitmap>()
    val backdrop: LiveData<Bitmap> get() = _backdrop

    fun init() = viewModelScope.launch {

    }

}