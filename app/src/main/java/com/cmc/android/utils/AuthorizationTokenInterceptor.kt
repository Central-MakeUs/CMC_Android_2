package com.cmc.android.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader("X-AUTH-TOKEN", "")

        return chain.proceed(builder.build())
    }
}