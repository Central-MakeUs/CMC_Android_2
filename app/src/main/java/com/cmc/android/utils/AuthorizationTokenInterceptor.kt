package com.cmc.android.utils

import android.util.Log
import com.cmc.android.utils.ApplicationClass.Companion.Authorization_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val jwtToken: String? = getJwt()

        jwtToken?.let{
            // builder.addHeader(Authorization_TOKEN, "Bearer $jwtToken")
            // Log.d("interceptor","Bearer $jwtToken")
        }

        return chain.proceed(builder.build())
    }
}