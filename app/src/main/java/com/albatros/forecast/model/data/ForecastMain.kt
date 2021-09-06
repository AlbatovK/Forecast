package com.albatros.forecast.model.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastMain(
    @SerializedName("now")
    @Expose
    var now: Int? = null,

    @SerializedName("now_dt")
    @Expose
    var nowDt: String? = null,

    @SerializedName("info")
    @Expose
    var info: Info? = null,

    @SerializedName("fact")
    @Expose
    var fact: Fact? = null,

    @SerializedName("forecast")
    @Expose
    var forecast: Forecast? = null,
)