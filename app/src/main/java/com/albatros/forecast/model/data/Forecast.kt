package com.albatros.forecast.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "forecast")
data class Forecast(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "date")
    @SerializedName("date")
    @Expose
    var date: String = "",

    @ColumnInfo(name = "date_ts")
    @SerializedName("date_ts")
    @Expose
    var dateTs: Int = 0,

    @ColumnInfo(name = "week")
    @SerializedName("week")
    @Expose
    var week: Int = 0,

    @ColumnInfo(name = "sunrise")
    @SerializedName("sunrise")
    @Expose
    var sunrise: String = "",

    @ColumnInfo(name = "sunset")
    @SerializedName("sunset")
    @Expose
    var sunset: String = "",

    @ColumnInfo(name = "moon_code")
    @SerializedName("moon_code")
    @Expose
    var moonCode: Int = 0,

    @ColumnInfo(name = "moon_text")
    @SerializedName("moon_text")
    @Expose
    var moonText: String = "",

    @Ignore
    @SerializedName("parts")
    @Expose
    var parts: List<Part>? = null,
)