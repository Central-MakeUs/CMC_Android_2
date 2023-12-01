package com.cmc.android.utils

import com.cmc.android.utils.ApplicationClass.Companion.Authorization_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val response = chain.proceed(builder.build())

        if (getAccessToken() != null) {
            builder.addHeader(Authorization_TOKEN, "${getAccessToken()}")
        }

        return response
    }
}