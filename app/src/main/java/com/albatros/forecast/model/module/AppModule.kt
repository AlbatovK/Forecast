package com.albatros.forecast.model.module

import com.albatros.forecast.model.api.Api
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = "https://api.weather.yandex.ru"

const val apiKey = "X-Yandex-API-Key: e5422830-52b1-4b27-9f2a-d18afe05825d"

const val getPath = "/v2/informers"

private fun provideApi(retrofit: Retrofit)
    = retrofit.create(Api::class.java)

private fun provideRetrofit(factory: GsonConverterFactory)
   = Retrofit.Builder().let {
    it.baseUrl(baseUrl)
    it.addConverterFactory(factory)
    it.build()
}

private fun provideGsonFactory(gson: Gson)
    = GsonConverterFactory.create(gson)

private fun provideGson()
    = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

val appModule = module {
    single { provideApi(get()) }
    single { provideRetrofit(get()) }
    single { provideGsonFactory(get()) }
    single { provideGson() }
}