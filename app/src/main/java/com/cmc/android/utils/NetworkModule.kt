package com.cmc.android.utils

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class NetworkModule {
    companion object {
        private var retrofit: Retrofit? = null

        var gson = GsonBuilder().setLenient().create()

        fun getInstance(): Retrofit? {
            if (retrofit == null) {
                synchronized(this) {
                    val client: OkHttpClient = OkHttpClient.Builder()
                        .addNetworkInterceptor(AuthorizationTokenInterceptor())
                        .addNetworkInterceptor(
                            HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
                        )
                        .build()

                    retrofit = Retrofit.Builder()
                        .baseUrl(ApplicationClass.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(NullOnEmptyConverterFactory)
                        .client(client)
                        .build()
                }
            }

            return retrofit
        }
    }
}