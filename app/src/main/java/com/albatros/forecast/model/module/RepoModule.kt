package com.albatros.forecast.model.module

import android.content.Context
import com.albatros.forecast.model.repo.DatabaseRepository
import com.albatros.forecast.model.repo.LocationRepository
import com.albatros.forecast.model.repo.MainRepository
import com.albatros.forecast.model.repo.PreferencesRepository
import com.google.android.gms.location.LocationServices
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private fun provideLocationClient(context: Context) =
    LocationServices.getFusedLocationProviderClient(context)

private fun provideFirebaseAnalytics(context: Context) =
    FirebaseAnalytics.getInstance(context)

val repoModule = module {
    single { MainRepository(get(), get(), get(), get()) }
    single { DatabaseRepository(get(), get(), get(), get(), get()) }
    single { LocationRepository(get(), androidContext()) }
    single { PreferencesRepository(get()) }
    single { provideLocationClient(androidContext()) }
    single { provideFirebaseAnalytics(androidContext()) }
}