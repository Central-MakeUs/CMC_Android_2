package com.cmc.android_pt.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.cmc.android_pt.BuildConfig
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

        var sharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        mSharedPreferences = sharedPreferences

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(AuthorizationTokenInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}