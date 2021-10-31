package com.albatros.forecast.domain

import com.albatros.forecast.model.data.GradientType

private val stateMap = mapOf(
    "clear" to "Ясно",
    "partly-cloudy" to "Малооблачно",
    "cloudy" to "Облачно с \nпрояснениями",
    "overcast" to "Пасмурно",
    "drizzle" to "Морось",
    "light-rain" to "Небольшой \nдождь",
    "rain" to "Дождь",
    "moderate-rain" to "Умеренно \nсильный дождь",
    "heavy-rain" to "Сильный дождь",
    "continuous-heavy-rain" to "Длительный \nсильный дождь",
    "showers" to "Ливень",
    "wet-snow" to "Дождь со \nснегом",
    "light-snow" to "Небольшой снег",
    "snow" to "Снег",
    "snow-showers" to "Снегопад",
    "hail" to "Град",
    "thunderstorm" to "Гроза",
    "thunderstorm-with-rain" to "Дождь \nс грозой",
    "thunderstorm-with-hail" to "Гроза \nс градом",
)

private val dirMap = mapOf(
    "nw" to "Северо-западное",
    "n" to "Северное",
    "ne" to "Северо-восточное",
    "e" to "Восточное",
    "se" to "Юго-восточное",
    "s" to "Южное",
    "sw" to "Юго-западное",
    "w" to "Западное",
    "c" to "Штиль",
)

private val daytimeMap = mapOf(
    "night" to "Ночь",
    "morning" to "Утро",
    "day" to "День",
    "evening" to "Вечер",
)

val arrClear = arrayOf(
    "clear",
    "partly-cloudy"
)

val arrCloudy = arrayOf(
    "cloudy",
    "overcast",
    "drizzle",
    "light-rain",
    "rain",
    "moderate-rain",
    "heavy-rain",
    "continuous-heavy-rain",
    "showers",
)

var arrSnow = arrayOf(
    "wet-snow",
    "light-snow",
    "snow",
    "snow-showers",
)

val arrThunder = arrayOf(
    "hail",
    "thunderstorm",
    "thunderstorm-with-rain",
    "thunderstorm-with-hail",
)

fun String.conditionToType(): GradientType {
    return when (this) {
        in arrClear -> GradientType.TYPE_CLEAR
        in arrCloudy -> GradientType.TYPE_CLOUDY
        in arrSnow -> GradientType.TYPE_SNOW
        in arrThunder -> GradientType.TYPE_THUNDER
        else -> GradientType.TYPE_CLEAR
    }
}

fun String.isWeatherDescription() = stateMap.containsKey(this)

fun String.getWeatherDescription(): String = stateMap[this]!!

fun String.isDaytimeDescription() = daytimeMap.containsKey(this)

fun String.getDaytimeDescription(): String = daytimeMap[this]!!

fun String.isDirection() = dirMap.containsKey(this)

fun String.getDirection(): String = dirMap[this]!!