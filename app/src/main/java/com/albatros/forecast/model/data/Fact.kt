package com.albatros.forecast.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fact")
data class Fact(
    @ColumnInfo(name = "temp")
    @SerializedName("temp")
    @Expose
    var temp: Int = 0,

    @ColumnInfo(name = "feels_like")
    @SerializedName("feels_like")
    @Expose
    var feelsLike: Int = 0,

    @ColumnInfo(name = "icon")
    @SerializedName("icon")
    @Expose
    var icon: String = "",

    @ColumnInfo(name = "condition")
    @SerializedName("condition")
    @Expose
    var condition: String = "",

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

    @ColumnInfo(name = "daytime")
    @SerializedName("daytime")
    @Expose
    var daytime: String = "",

    @ColumnInfo(name = "polar")
    @SerializedName("polar")
    @Expose
    var polar: Boolean = false,

    @ColumnInfo(name = "season")
    @SerializedName("season")
    @Expose
    var season: String = "",

    @ColumnInfo(name = "obs_time")
    @PrimaryKey(autoGenerate = false)
    @SerializedName("obs_time")
    @Expose
    var obsTime: Double = 0.0,
)