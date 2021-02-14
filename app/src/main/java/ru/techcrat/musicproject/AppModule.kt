package ru.techcrat.musicproject

import org.koin.dsl.module
import ru.techcrat.musicproject.retrofit.SpotifyTokenService
import ru.techcrat.musicproject.retrofit.networkModule
import ru.techcrat.musicproject.ui.main.uiModule

val appModule = uiModule + networkModule + module {
    single {
        SpotifyTokenService()
    }
}