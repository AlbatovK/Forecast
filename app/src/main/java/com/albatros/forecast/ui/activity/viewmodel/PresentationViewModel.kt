package com.albatros.forecast.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.repo.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PresentationViewModel(private val repo: MainRepository) : ViewModel() {
    private val _forecast = MutableLiveData<ForecastMain>().apply {
        viewModelScope.launch(Dispatchers.Main) {
            value = repo.getForecast()
        }
    }

    val forecast: LiveData<ForecastMain> = _forecast

    fun refreshData() {
        viewModelScope.launch(Dispatchers.Main) {
            _forecast.value = repo.getForecast(refresh = true)
        }
    }
}
