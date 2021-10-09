package com.albatros.forecast

import com.albatros.forecast.domain.gradient.GradientType
import com.albatros.forecast.domain.gradient.conditionToType
import com.albatros.forecast.domain.isDaytimeDescription
import com.albatros.forecast.domain.isDirection
import com.albatros.forecast.domain.isWeatherDescription
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTest {

    @Test
    fun stringExtensions_areCorrect() {
        val dayTimeIsWeather = "midnight".isWeatherDescription()
        assertEquals(false, dayTimeIsWeather)
        val weatherIsDayTime = "rain".isDaytimeDescription()
        assertEquals(false, weatherIsDayTime)
        val correctDayTime = "day".isDaytimeDescription()
        assertEquals(true, correctDayTime)
        val correctWeather = "snow".isWeatherDescription()
        assertEquals(true, correctWeather)
        val correctDirection = listOf("c", "n", "e", "w", "s").all { it.isDirection() }
        assertEquals(true, correctDirection)
    }

    @Test
    fun gradientExtensions_areCorrect() {
        val input = arrayOf("clear", "cloudy", "snow", "thunderstorm")
        val expected = arrayOf(
            GradientType.TYPE_CLEAR,
            GradientType.TYPE_CLOUDY,
            GradientType.TYPE_SNOW,
            GradientType.TYPE_THUNDER)
        val actual = input.map { it.conditionToType() }.toTypedArray()
        assertArrayEquals(expected, actual)
    }
}