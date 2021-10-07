package com.albatros.forecast

import com.albatros.forecast.domain.isDaytimeDescription
import com.albatros.forecast.domain.isDirection
import com.albatros.forecast.domain.isWeatherDescription
import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTest {

    @Test
    fun addition_isCorrect() {
        val num = 2 + 2
        assertEquals(4, num)
    }

    @Test
    fun subtraction_isCorrect() {
        val num = 2 - 2
        assertEquals(0, num)
    }

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
}