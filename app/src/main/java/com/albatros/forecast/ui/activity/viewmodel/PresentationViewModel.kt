package com.albatros.forecast.ui.activity.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.albatros.forecast.domain.conditionToType
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.data.GradientType
import com.albatros.forecast.model.repo.MainRepository
import com.albatros.forecast.model.repo.PreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PresentationViewModel(
    private val repo: MainRepository,
    private val settings: PreferencesRepository
) : ViewModel() {

    private val forecastObserver: Observer<ForecastMain> = Observer {
        _gradient.value = if (isChangingEnabled())
            forecast.value?.fact?.condition?.conditionToType() ?: GradientType.TYPE_CLEAR
        else getRegularGradient()
        Log.d("ViewModel", "GradientChanged")
    }

    private lateinit var _forecast: MutableLiveData<ForecastMain>

    lateinit var forecast: LiveData<ForecastMain>

    private val _gradient = MutableLiveData<GradientType>()

    val gradient: LiveData<GradientType> = _gradient

    fun refreshData() {
        viewModelScope.launch(Dispatchers.Main) {
            _forecast.value = repo.getForecast(refresh = true)
        }
    }

    fun disableGradientChanging() = settings.putGradientMode(PreferencesRepository.MODE_NOT_CHANGE)

    fun enableGradientChanging() =  settings.putGradientMode(PreferencesRepository.MODE_CHANGE)

    fun setRegularGradient(type: GradientType) {
        settings.putGradientType(type)
        _gradient.value = type
    }

    private fun getRegularGradient() = settings.getGradientType()

    fun isChangingEnabled() = settings.isChangingEnabled()

    override fun onCleared() {
        _forecast.removeObserver(forecastObserver)
        super.onCleared()
    }

    fun init() {
        _forecast = MutableLiveData<ForecastMain>().apply {
            viewModelScope.launch(Dispatchers.Main) {
                value = repo.getForecast()
            }
        }
        forecast = _forecast
        forecast.observeForever(forecastObserver)
        Log.d("ViewModel", "Observing")
    }

    fun errorInit() {
        _forecast = MutableLiveData<ForecastMain>().apply {
            viewModelScope.launch(Dispatchers.Main) {
                value = repo.getForecast(empty = true)
            }
        }
        forecast = _forecast
        forecast.observeForever(forecastObserver)
        Log.d("ViewModel", "ErrorInit")
    }
}
