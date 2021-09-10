package com.albatros.forecast.model.database.forecastMain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.albatros.forecast.model.data.ForecastMain

@Dao
interface ForecastMainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: ForecastMain)

    @Delete
    suspend fun deleteForecast(forecast: ForecastMain)

    @Update
    suspend fun updateForecast(forecast: ForecastMain)

    @Query("Delete From forecast_main")
    suspend fun clearTable()

    @Query("Select * From forecast_main")
    suspend fun getAll(): List<ForecastMain>
}