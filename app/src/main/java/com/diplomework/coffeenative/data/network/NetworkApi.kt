package com.diplomework.coffeenative.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkApi {

    // base server's url
    const val BASE_URL = "http://35.246.250.120:80/"
    const val PIC_STORAGE_URL = "/storage/image"

    private val logging = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            this.level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun createService(): IApiService {
        return retrofit.create(IApiService::class.java)
    }
}