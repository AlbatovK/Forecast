package com.albatros.forecast.ui.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albatros.forecast.domain.gradient.GradientType
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.repo.MainRepository
import com.albatros.forecast.model.repo.PreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PresentationViewModel(private val repo: MainRepository, private val settings: PreferencesRepository) : ViewModel() {
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

    fun disableGradientChanging() = settings.putGradientMode(PreferencesRepository.MODE_NOT_CHANGE)

    fun enableGradientChanging() = settings.putGradientMode(PreferencesRepository.MODE_CHANGE)

    fun setRegularGradient(type: GradientType) = settings.putGradientType(type)

    fun getRegularGradient() = settings.getGradientType()
}
