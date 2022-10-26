package com.smartflowtech.cupidcustomerapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NetworkClient @Inject constructor(
    val moshiConverterFactory: MoshiConverterFactory,
) {

    private val loggerInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient by lazy {
        OkHttpClient.Builder().apply {
            this
                .addInterceptor(loggerInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
        }.build()
    }

    inline fun <reified T> getApiService(
        baseUrl: String
    ): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create(T::class.java)
    }

}