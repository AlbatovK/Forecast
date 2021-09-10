package com.albatros.forecast.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "forecast_main")
data class ForecastMain(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "now")
    @SerializedName("now")
    @Expose
    var now: Int = 0,

    @ColumnInfo(name = "now_dt")
    @SerializedName("now_dt")
    @Expose
    var nowDt: String = "",

    @Ignore
    @SerializedName("info")
    @Expose
    var info: Info? = null,

    @Ignore
    @SerializedName("fact")
    @Expose
    var fact: Fact? = null,

    @Ignore
    @SerializedName("forecast")
    @Expose
    var forecast: Forecast? = null,
)