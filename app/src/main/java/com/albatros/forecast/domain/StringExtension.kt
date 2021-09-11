package com.albatros.forecast.domain

private val stateMap = mapOf(
    "clear"                  to "Ясно",
    "partly-cloudy"          to "Малооблачно",
    "cloudy"                 to "Облачно с прояснениями",
    "overcast"               to "Пасмурно",
    "drizzle"                to "Морось",
    "light-rain"             to "Небольшой дождь",
    "rain"                   to "Дождь",
    "moderate-rain"          to "Умеренно сильный дождь",
    "heavy-rain"             to "Сильный дождь",
    "continuous-heavy-rain"  to "Длительный сильный дождь",
    "showers"                to "Ливень",
    "wet-snow"               to "Дождь со снегом",
    "light-snow"             to "Небольшой снег",
    "snow"                   to "Снег",
    "snow-showers"           to "Снегопад",
    "hail"                   to "Град",
    "thunderstorm"           to "Гроза",
    "thunderstorm-with-rain" to "Дождь с грозой",
    "thunderstorm-with-hail" to "Гроза с градом",
)

fun String.isWeatherDescription() = stateMap.containsKey(this)

fun String.getWeatherDescription(): String = stateMap[this]!!

private val daytimeMap = mapOf(
    "night"   to "Ночь",
    "morning" to "Утро",
    "day"     to "День",
    "evening" to "Вечер",
)

fun String.isDaytimeDescription() = daytimeMap.containsKey(this)

fun String.getDaytimeDescription(): String = daytimeMap[this]!!