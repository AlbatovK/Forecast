package com.albatros.forecast.model.module

import com.albatros.forecast.model.repo.DatabaseRepository
import com.albatros.forecast.model.repo.LocationRepository
import com.albatros.forecast.model.repo.MainRepository
import com.albatros.forecast.model.repo.PreferencesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repoModule = module {
    single { MainRepository(get(), get(), get(), get()) }
    single { DatabaseRepository(get(), get(), get(), get(), get()) }
    single { LocationRepository(get(), androidContext()) }
    single { PreferencesRepository(get()) }
}