package com.albatros.forecast.model.repo

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
        forecast.fact?.let { factDao.insertFact(it) }
        forecast.info?.let { infoDao.insertInfo(it) }
        forecast.forecast?.let { forecastDao.insertForecast(it) }
        forecast.forecast?.parts?.let { partDao.insertPart(it[0], it[1]) }
        forecastMainDao.insertForecast(forecast)

    }

    private suspend fun clearDatabase() {
        factDao.clearTable()
        infoDao.clearTable()
        forecastMainDao.clearTable()
        forecastDao.clearTable()
        partDao.clearTable()
    }

    suspend fun collectForecastFromDatabase(): ForecastMain {
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
        }
    }
}
