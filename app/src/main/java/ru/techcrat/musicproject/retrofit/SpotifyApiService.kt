package ru.techcrat.musicproject.retrofit

import SpotifySong
import Track
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface SpotifyApiService {
    @GET("tracks")
    suspend fun getSongs():Response<SpotifySong>
}
