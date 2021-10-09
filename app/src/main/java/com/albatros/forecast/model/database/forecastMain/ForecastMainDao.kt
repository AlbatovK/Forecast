package com.albatros.forecast.model.database.forecastMain

import androidx.room.*
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.database.dao.BaseDao

@Dao
interface ForecastMainDao : BaseDao<ForecastMain> {

    @Query("Delete From forecast_main")
    suspend fun clearTable()

    @Query("Select * From forecast_main")
    suspend fun getAll(): List<ForecastMain>
}