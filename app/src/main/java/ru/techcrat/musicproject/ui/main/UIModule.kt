package ru.techcrat.musicproject.ui.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { MainViewModule(get()) }
}