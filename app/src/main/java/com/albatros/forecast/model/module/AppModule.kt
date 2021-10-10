package com.albatros.forecast.model.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.albatros.forecast.model.api.Api
import com.albatros.forecast.model.database.impl.ForecastDatabase
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = "https://api.weather.yandex.ru"

const val apiKey = "X-Yandex-API-Key: e5422830-52b1-4b27-9f2a-d18afe05825d"

const val getPath = "/v2/informers"

private const val dbName = "note-database"

fun provideDatabase(context: Context) =
    Room.databaseBuilder(context, ForecastDatabase::class.java, dbName)
        .setJournalMode(RoomDatabase.JournalMode.AUTOMATIC).build()

fun provideFactDao(db: ForecastDatabase) =
    db.getFactDao()

fun provideForecastDao(db: ForecastDatabase) =
    db.getForecastDao()

fun provideForecastMainDao(db: ForecastDatabase) =
    db.getForecastMainDao()

fun provideInfoDao(db: ForecastDatabase) =
    db.getInfoDao()

fun providePartDao(db: ForecastDatabase) =
    db.getPartDao()

private fun provideApi(retrofit: Retrofit) =
    retrofit.create(Api::class.java)

private fun provideRetrofit(factory: GsonConverterFactory) = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(factory)
    .build()

private fun provideGsonFactory(gson: Gson) =
    GsonConverterFactory.create(gson)

private fun provideGson() =
    GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).setPrettyPrinting().create()

val appModule = module {
    single { provideDatabase(androidContext()) }

    single { provideFactDao(get()) }
    single { provideForecastDao(get()) }
    single { provideInfoDao(get()) }
    single { provideForecastMainDao(get()) }
    single { providePartDao(get()) }

    single { provideApi(get()) }
    single { provideRetrofit(get()) }

    single { provideGsonFactory(get()) }
    single { provideGson() }
}