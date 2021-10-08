package com.albatros.forecast.ui.fragment.viewmodel

import androidx.lifecycle.*
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.repo.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstViewModel(private val repo: MainRepository) : ViewModel() {

    private val observer = Observer<ForecastMain> { _forecast.value = it }

    init {
        repo.forecastLive.observeForever(observer)
    }

    private val _forecast = MutableLiveData<ForecastMain>().apply {
        viewModelScope.launch(Dispatchers.Main) {
            value = repo.getForecast()
        }
    }

    override fun onCleared() {
        repo.forecastLive.removeObserver(observer)
        super.onCleared()
    }

    val forecast: LiveData<ForecastMain> = _forecast
}