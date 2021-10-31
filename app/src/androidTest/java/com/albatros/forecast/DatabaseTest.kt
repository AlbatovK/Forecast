package com.albatros.forecast

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.albatros.forecast.model.data.*
import com.albatros.forecast.model.database.impl.ForecastDatabase
import com.albatros.forecast.model.module.appModule
import com.albatros.forecast.model.module.provideDatabase
import com.albatros.forecast.model.module.repoModule
import com.albatros.forecast.model.repo.DatabaseRepository
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.inject

@RunWith(AndroidJUnit4::class)
class DatabaseTest : TestCase() {

    private lateinit var forecastDb: ForecastDatabase
    private lateinit var actualForecast: ForecastMain
    private val modules = listOf(appModule, repoModule)

    init {
        loadKoinModules(modules)
    }

    private val dbRepo: DatabaseRepository by inject(DatabaseRepository::class.java)

    @Before
    public override fun setUp() = runBlocking(Dispatchers.IO) {
        actualForecast = dbRepo.collectForecastFromDatabase()
        forecastDb = provideDatabase(InstrumentationRegistry.getInstrumentation().targetContext)
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
        } catch (e: Exception) { fail() }
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
        unloadKoinModules(modules)
    }
}