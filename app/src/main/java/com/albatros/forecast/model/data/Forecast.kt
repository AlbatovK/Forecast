package com.albatros.forecast.model.data

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Forecast(
    @SerializedName("date")
    @Expose
    var date: String? = null,

    @SerializedName("date_ts")
    @Expose
    var dateTs: Int? = null,

    @SerializedName("week")
    @Expose
    var week: Int? = null,

    @SerializedName("sunrise")
    @Expose
    var sunrise: String? = null,

    @SerializedName("sunset")
    @Expose
    var sunset: String? = null,

    @SerializedName("moon_code")
    @Expose
    var moonCode: Int? = null,

    @SerializedName("moon_text")
    @Expose
    var moonText: String? = null,

    @SerializedName("parts")
    @Expose
    var parts: List<Part>? = null,
)