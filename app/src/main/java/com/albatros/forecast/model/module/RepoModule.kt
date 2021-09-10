package com.albatros.forecast.model.module

import com.albatros.forecast.model.repo.DatabaseRepository
import com.albatros.forecast.model.repo.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single { MainRepository(get(), get()) }
    single { DatabaseRepository(get(), get(), get(), get(), get()) }
}