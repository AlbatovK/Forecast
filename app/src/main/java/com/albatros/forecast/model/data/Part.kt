package com.albatros.forecast.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Part(
    @SerializedName("part_name")
    @Expose
    var partName: String? = null,

    @SerializedName("temp_min")
    @Expose
    var tempMin: Double? = null,

    @SerializedName("temp_max")
    @Expose
    var tempMax: Double? = null,

    @SerializedName("temp_avg")
    @Expose
    var tempAvg: Double? = null,

    @SerializedName("feels_like")
    @Expose
    var feelsLike: Double? = null,

    @SerializedName("icon")
    @Expose
    var icon: String? = null,

    @SerializedName("condition")
    @Expose
    var condition: String? = null,

    @SerializedName("daytime")
    @Expose
    var daytime: String? = null,

    @SerializedName("polar")
    @Expose
    var polar: Boolean? = null,

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

    @SerializedName("prec_mm")
    @Expose
    var precMm: Double? = null,

    @SerializedName("prec_period")
    @Expose
    var precPeriod: Double? = null,

    @SerializedName("prec_prob")
    @Expose
    var precProb: Double? = null,
)