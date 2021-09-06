package com.albatros.forecast.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.repo.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstViewModel(private val repo: MainRepository) : ViewModel() {

    private val _forecast = MutableLiveData<ForecastMain>().apply {
        viewModelScope.launch(Dispatchers.Main) {
            value = try {
                repo.getForecast()
            } catch (e: Exception) {
                ForecastMain()
            }
        }
    }

    val forecast: LiveData<ForecastMain> = _forecast
}