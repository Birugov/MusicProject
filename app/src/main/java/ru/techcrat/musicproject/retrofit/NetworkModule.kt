package ru.techcrat.musicproject.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { SpotifyTokenInterceptor(get()) }
    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<SpotifyTokenInterceptor>())
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    factory {
        Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/me/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(SpotifyApiService::class.java)
    }
}
