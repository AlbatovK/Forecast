package com.albatros.forecast.model.database.forecast

import androidx.room.Dao
import androidx.room.Query
import com.albatros.forecast.model.data.Forecast
import com.albatros.forecast.model.database.dao.BaseDao

@Dao
interface ForecastDao : BaseDao<Forecast> {

    @Query("Delete From forecast")
    suspend fun clearTable()

    @Query("Select * From forecast")
    suspend fun getAll(): List<Forecast>
}