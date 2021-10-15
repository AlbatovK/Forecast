package com.albatros.forecast.model.repo

import android.content.SharedPreferences
import com.albatros.forecast.domain.gradient.GradientType

class PreferencesRepository(private val settings: SharedPreferences) {

    private val editor = settings.edit()

    companion object {
        const val KEY_GRADIENT_CHANGE_MODE = "key_gradient_change_mode"
        const val MODE_CHANGE = "mode_change"
        const val MODE_NOT_CHANGE = "mode_not_change"

        const val KEY_GRADIENT_TYPE = "key_gradient_type"
        const val TYPE_CLEAR = "type_clear"
        const val TYPE_CLOUDY = "type_cloudy"
        const val TYPE_SNOW = "type_snow"
        const val TYPE_THUNDER = "type_thunder"

        val changeModeConstants = arrayOf(MODE_CHANGE, MODE_NOT_CHANGE)
        val typeConstants = arrayOf(TYPE_CLEAR, TYPE_CLOUDY, TYPE_SNOW, TYPE_THUNDER)

        const val exception_msg = "PreferencesRepository: Not present key type"
    }

    fun putGradientMode(value: String) {
        if (value !in changeModeConstants)
            throw Exception(exception_msg)
        with(editor) {
            putString(KEY_GRADIENT_CHANGE_MODE, value)
            apply()
        }
    }

    fun getGradientMode() = settings.getString(KEY_GRADIENT_CHANGE_MODE, MODE_CHANGE)

    fun putGradientType(type: GradientType) {
        val value = when (type) {
            GradientType.TYPE_CLEAR -> TYPE_CLEAR
            GradientType.TYPE_CLOUDY -> TYPE_CLOUDY
            GradientType.TYPE_SNOW -> TYPE_SNOW
            GradientType.TYPE_THUNDER -> TYPE_THUNDER
        }
        with(editor) {
            putString(KEY_GRADIENT_TYPE, value)
            apply()
        }
    }

    fun getGradientType(): GradientType = when (settings.getString(KEY_GRADIENT_TYPE, TYPE_CLEAR)) {
        TYPE_CLEAR -> GradientType.TYPE_CLEAR
        TYPE_CLOUDY -> GradientType.TYPE_CLOUDY
        TYPE_SNOW -> GradientType.TYPE_SNOW
        TYPE_THUNDER -> GradientType.TYPE_THUNDER
        else -> GradientType.TYPE_CLEAR
    }
}