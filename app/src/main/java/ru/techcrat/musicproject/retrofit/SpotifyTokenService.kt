package ru.techcrat.musicproject.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SpotifyTokenService {
    private val _tokenLiveData = MutableLiveData<String?>(null)
    val tokenLiveData: LiveData<String?> = _tokenLiveData

    fun updateToken(token: String?) {
        _tokenLiveData.postValue(token)
    }
}