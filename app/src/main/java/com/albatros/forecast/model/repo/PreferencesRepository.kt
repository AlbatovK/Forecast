package com.albatros.forecast.model.repo

import android.content.SharedPreferences
import com.albatros.forecast.model.data.GradientType

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

        const val KEY_SEND = "key_send"
        const val MODE_NOT_SEND = "mode_not_send"
        const val MODE_SEND = "mode_send"

        val changeModeConstants = arrayOf(MODE_CHANGE, MODE_NOT_CHANGE)

        const val exception_msg = "PreferencesRepository: No present key type"
    }

    fun putGradientMode(value: String) {
        if (value !in changeModeConstants)
            throw Exception(exception_msg)
        with(editor) {
            putString(KEY_GRADIENT_CHANGE_MODE, value)
            apply()
        }
    }

    private fun getGradientMode() = settings.getString(KEY_GRADIENT_CHANGE_MODE, MODE_CHANGE)

    private fun getSendingMode() = settings.getString(KEY_SEND, MODE_SEND)

    fun isChangingEnabled() = getGradientMode() == MODE_CHANGE

    fun isChangingDisabled() = getGradientMode() == MODE_NOT_CHANGE

    fun isSendingEnabled() = getSendingMode() == MODE_SEND

    fun isSendingDisabled() = getSendingMode() == MODE_NOT_SEND

    fun disableGradientChanging() = putGradientMode(MODE_NOT_CHANGE)

    fun enableGradientChanging() = putGradientMode(MODE_CHANGE)

    fun enableSending(): Unit = with(editor) {
        putString(KEY_SEND, MODE_SEND)
        apply()
    }

    fun disableSending(): Unit = with(editor) {
        putString(KEY_SEND, MODE_NOT_SEND)
        apply()
    }

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
        else -> throw IllegalArgumentException(exception_msg)
    }
}