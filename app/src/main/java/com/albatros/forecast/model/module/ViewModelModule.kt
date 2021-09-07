package com.albatros.forecast.model.module

import com.albatros.forecast.ui.fragment.viewmodel.FirstViewModel
import com.albatros.forecast.ui.activity.viewmodel.PresentationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FirstViewModel(get()) }
    viewModel { PresentationViewModel(get()) }
}