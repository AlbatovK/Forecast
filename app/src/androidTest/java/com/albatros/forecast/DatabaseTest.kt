package com.albatros.forecast

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.albatros.forecast.model.data.*
import com.albatros.forecast.model.database.impl.ForecastDatabase
import com.albatros.forecast.model.module.*
import com.albatros.forecast.model.repo.DatabaseRepository
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest : TestCase() {

    private lateinit var forecastDb: ForecastDatabase
    private lateinit var dbRepo: DatabaseRepository
    private lateinit var actualForecast: ForecastMain

    @Before
    public override fun setUp() = runBlocking(Dispatchers.IO) {
        val context: Context = ApplicationProvider.getApplicationContext()
        forecastDb = provideDatabase(context)
        dbRepo = DatabaseRepository(
            provideFactDao(forecastDb),
            provideForecastDao(forecastDb),
            provideForecastMainDao(forecastDb),
            provideInfoDao(forecastDb),
            providePartDao(forecastDb)
        )
        actualForecast = dbRepo.collectForecastFromDatabase()
    }

    @Test(timeout = 300)
    fun testCollection() = runBlocking(Dispatchers.IO) {
        val partList = listOf(Part(), Part())
        val forecast = ForecastMain(
            info = Info(),
            fact = Fact(),
            forecast = Forecast(parts = partList))
        dbRepo.insertForecast(forecast)
        val equals = forecast.now == dbRepo.collectForecastFromDatabase().now
        assertEquals(equals, true)
    }

    @Test(timeout = 300)
    fun testClear(): Unit = runBlocking(Dispatchers.IO) {
        try {
            dbRepo.clearDatabase()
            dbRepo.collectForecastFromDatabase()
        } catch (e: Exception) {
            fail()
        }
    }

    @Test(timeout = 300)
    fun testInsertion() = runBlocking(Dispatchers.IO) {
        if (dbRepo.getItemsCount() > 0)
            dbRepo.clearDatabase()
        dbRepo.insertForecast(ForecastMain())
        assertEquals(1, dbRepo.getItemsCount())
    }

    @After
    fun closeDatabase() = runBlocking(Dispatchers.IO) {
        dbRepo.insertForecast(actualForecast)
        forecastDb.close()
    }
}