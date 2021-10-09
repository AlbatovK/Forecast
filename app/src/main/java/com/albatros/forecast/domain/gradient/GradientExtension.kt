package com.albatros.forecast.domain.gradient

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import com.albatros.forecast.R.color as Colors

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

fun makeGradient(
    type: GradientType,
    res: Resources,
    theme: Resources.Theme
): GradientDrawable {
    val colors: IntArray = when (type) {
        GradientType.TYPE_CLEAR -> intArrayOf(res.getColor(Colors.sky_dark, theme), res.getColor(Colors.sky_light, theme))
        GradientType.TYPE_CLOUDY ->  intArrayOf(res.getColor(Colors.cloud_light, theme), res.getColor(Colors.cloud_dark, theme))
        else -> intArrayOf(Colors.sky_dark, Colors.sky_light)
    }
    return GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)
}