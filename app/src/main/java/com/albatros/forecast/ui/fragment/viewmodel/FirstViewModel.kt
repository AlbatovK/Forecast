package com.albatros.forecast.ui.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.repo.MainRepository

class FirstViewModel(private val repo: MainRepository) : ViewModel() {

    private val observer = Observer<ForecastMain> { value -> _forecast?.let { it.value = value  } }

    init {
        repo?.forecastLive?.observeForever(observer)
    }

    private val _forecast = MutableLiveData<ForecastMain>()

    override fun onCleared() {
        repo.forecastLive.removeObserver(observer)
        super.onCleared()
    }

    val forecast: LiveData<ForecastMain> = _forecast
}