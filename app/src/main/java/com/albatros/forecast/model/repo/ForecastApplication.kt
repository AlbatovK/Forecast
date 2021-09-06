package com.albatros.forecast.model.repo

import android.app.Application
import com.albatros.forecast.model.module.appModule
import com.albatros.forecast.model.module.repoModule
import com.albatros.forecast.model.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ForecastApplication : Application() {

    private val modules = listOf(appModule, repoModule, viewModelModule)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ForecastApplication)
            modules(modules)
        }
    }
}