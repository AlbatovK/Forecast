package com.albatros.forecast.model.database.forecast

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.albatros.forecast.model.data.Forecast

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: Forecast)

    @Delete
    suspend fun deleteForecast(forecast: Forecast)

    @Update
    suspend fun updateForecast(forecast: Forecast)

    @Query("Delete From forecast")
    suspend fun clearTable()

    @Query("Select * From forecast")
    suspend fun getAll(): List<Forecast>
}