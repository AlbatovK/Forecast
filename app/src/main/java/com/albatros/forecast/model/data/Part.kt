package com.albatros.forecast.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "part")
data class Part(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "part_name")
    @SerializedName("part_name")
    @Expose
    var partName: String = "",

    @ColumnInfo(name = "temp_min")
    @SerializedName("temp_min")
    @Expose
    var tempMin: Double = 0.0,

    @ColumnInfo(name = "temp_max")
    @SerializedName("temp_max")
    @Expose
    var tempMax: Double = 0.0,

    @ColumnInfo(name = "temp_avg")
    @SerializedName("temp_avg")
    @Expose
    var tempAvg: Double = 0.0,

    @ColumnInfo(name = "feels_like")
    @SerializedName("feels_like")
    @Expose
    var feelsLike: Double = 0.0,

    @ColumnInfo(name = "icon")
    @SerializedName("icon")
    @Expose
    var icon: String = "",

    @ColumnInfo(name = "condition")
    @SerializedName("condition")
    @Expose
    var condition: String = "",

    @ColumnInfo(name = "daytime")
    @SerializedName("daytime")
    @Expose
    var daytime: String = "",

    @ColumnInfo(name = "polar")
    @SerializedName("polar")
    @Expose
    var polar: Boolean? = null,

    @ColumnInfo(name = "wind_speed")
    @SerializedName("wind_speed")
    @Expose
    var windSpeed: Double = 0.0,

    @ColumnInfo(name = "wind_gust")
    @SerializedName("wind_gust")
    @Expose
    var windGust: Double = 0.0,

    @ColumnInfo(name = "wind_dir")
    @SerializedName("wind_dir")
    @Expose
    var windDir: String = "",

    @ColumnInfo(name = "pressure_mm")
    @SerializedName("pressure_mm")
    @Expose
    var pressureMm: Double = 0.0,

    @ColumnInfo(name = "pressure_pa")
    @SerializedName("pressure_pa")
    @Expose
    var pressurePa: Double = 0.0,

    @ColumnInfo(name = "humidity")
    @SerializedName("humidity")
    @Expose
    var humidity: Double = 0.0,

    @ColumnInfo(name = "prec_mm")
    @SerializedName("prec_mm")
    @Expose
    var precMm: Double = 0.0,

    @ColumnInfo(name = "prec_period")
    @SerializedName("prec_period")
    @Expose
    var precPeriod: Double = 0.0,

    @ColumnInfo(name = "prec_prob")
    @SerializedName("prec_prob")
    @Expose
    var precProb: Double = 0.0,
)