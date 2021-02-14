package ru.techcrat.musicproject.ui.main

import androidx.lifecycle.ViewModel
import ru.techcrat.musicproject.retrofit.SpotifyTokenService

class MainViewModule(val tokenService: SpotifyTokenService) : ViewModel() {
    fun onTokenObtained(token: String?) {
        tokenService.updateToken(token)
    }
}