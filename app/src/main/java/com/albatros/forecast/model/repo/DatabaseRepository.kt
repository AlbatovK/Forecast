package com.albatros.forecast.model.repo

import android.util.Log
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.database.fact.FactDao
import com.albatros.forecast.model.database.forecast.ForecastDao
import com.albatros.forecast.model.database.forecastMain.ForecastMainDao
import com.albatros.forecast.model.database.info.InfoDao
import com.albatros.forecast.model.database.part.PartDao

class DatabaseRepository(
    private val factDao: FactDao,
    private val forecastDao: ForecastDao,
    private val forecastMainDao: ForecastMainDao,
    private val infoDao: InfoDao,
    private val partDao: PartDao,
) {

    suspend fun insertForecast(forecast: ForecastMain) {
        clearDatabase()
        forecast.fact?.let { factDao.insert(it) }
        forecast.info?.let { infoDao.insert(it) }
        forecast.forecast?.let { forecastDao.insert(it) }
        forecast.forecast?.parts?.let { partDao.insert(it[0], it[1]) }
        forecastMainDao.insert(forecast)
        Log.d("DatabaseRepository", "insertForecast: added item to db - $forecast")
    }

    suspend fun clearDatabase() {
        factDao.clearTable()
        infoDao.clearTable()
        forecastMainDao.clearTable()
        forecastDao.clearTable()
        partDao.clearTable()
        Log.d("DatabaseRepository", "clearDatabase: cleared db")
    }

    suspend fun getItemsCount() = forecastMainDao.getAll().size

    suspend fun collectForecastFromDatabase(): ForecastMain {
        if (getItemsCount() == 0)
            return ForecastMain()
        val main = forecastMainDao.getAll()[0]
        val info = infoDao.getAll()[0]
        val fact = factDao.getAll()[0]
        val parts = partDao.getAll()
        val forecast = forecastDao.getAll()[0]
        forecast.parts = parts
        return main.apply {
            this.info = info
            this.forecast = forecast
            this.fact = fact
        }.also {
            Log.d(
                "DatabaseRepository",
                "collectForecastFromDatabase: collected forecast - $forecast"
            )
        }
    }
}
