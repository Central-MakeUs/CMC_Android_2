package com.cmc.android.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.cmc.android.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {
    companion object {
        const val Authorization_TOKEN: String = "X-AUTH-TOKEN"
        const val BASE_URL = BuildConfig.base_url
        const val TAG: String = "cmc-pref"

        lateinit var retrofit: Retrofit
        lateinit var mSharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }
}