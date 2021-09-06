package com.albatros.forecast.model.module

import com.albatros.forecast.ui.FirstViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FirstViewModel(get()) }
}