package com.albatros.forecast.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Fact(
    @SerializedName("temp")
    @Expose
    var temp: Double? = null,

    @SerializedName("feels_like")
    @Expose
    var feelsLike: Double? = null,

    @SerializedName("icon")
    @Expose
    var icon: String? = null,

    @SerializedName("condition")
    @Expose
    var condition: String? = null,

    @SerializedName("wind_speed")
    @Expose
    var windSpeed: Double? = null,

    @SerializedName("wind_gust")
    @Expose
    var windGust: Double? = null,

    @SerializedName("wind_dir")
    @Expose
    var windDir: String? = null,

    @SerializedName("pressure_mm")
    @Expose
    var pressureMm: Double? = null,

    @SerializedName("pressure_pa")
    @Expose
    var pressurePa: Double? = null,

    @SerializedName("humidity")
    @Expose
    var humidity: Double? = null,

    @SerializedName("daytime")
    @Expose
    var daytime: String? = null,

    @SerializedName("polar")
    @Expose
    var polar: Boolean? = null,

    @SerializedName("season")
    @Expose
    var season: String? = null,

    @SerializedName("obs_time")
    @Expose
    var obsTime: Double? = null,
)