package com.albatros.forecast.model.database.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.albatros.forecast.model.data.*
import com.albatros.forecast.model.database.fact.FactDao
import com.albatros.forecast.model.database.forecast.ForecastDao
import com.albatros.forecast.model.database.forecastMain.ForecastMainDao
import com.albatros.forecast.model.database.info.InfoDao
import com.albatros.forecast.model.database.part.PartDao

@Database(
    entities = [ForecastMain::class, Forecast::class, Fact::class, Part::class, Info::class],
    version = 1,
    exportSchema = false
)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun getForecastMainDao(): ForecastMainDao

    abstract fun getForecastDao(): ForecastDao

    abstract fun getPartDao(): PartDao

    abstract fun getInfoDao(): InfoDao

    abstract fun getFactDao(): FactDao
}