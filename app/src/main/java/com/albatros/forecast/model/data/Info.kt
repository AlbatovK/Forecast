package com.albatros.forecast.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "info")
data class Info(
    @ColumnInfo(name = "lat")
    @SerializedName("lat")
    @Expose
    var lat: Double = 0.0,

    @ColumnInfo(name = "lon")
    @SerializedName("lon")
    @Expose
    var lon: Double = 0.0,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose
    var url: String = "",
)