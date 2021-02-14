package ru.techcrat.musicproject.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class SpotifyTokenInterceptor(private val tokenService: SpotifyTokenService) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token: String = tokenService.tokenLiveData.value ?: return chain.proceed(request)
        return chain.proceed(
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        )
    }
}