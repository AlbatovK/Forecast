package com.albatros.forecast.model.data

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Info(
    @SerializedName("lat")
    @Expose
    var lat: Double? = null,

    @SerializedName("lon")
    @Expose
    var lon: Double? = null,

    @SerializedName("url")
    @Expose
    var url: String? = null,
)