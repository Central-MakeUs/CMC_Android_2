package com.cmc.android_pt.utils

import com.cmc.android_pt.utils.ApplicationClass.Companion.Authorization_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        // val response = chain.proceed(builder.build())

        if (getAccessToken() != null) {
            builder.addHeader(Authorization_TOKEN, "${getAccessToken()}")
        }

        return chain.proceed(builder.build())

//        if (response.code == 401) getNewToken()
//
//        return response
    }

//    private fun getNewToken() {
//        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
//        val getNewTokenInterface = retrofit.create(AuthInterface::class.java)
//        getNewTokenInterface.getNewToken(getRefreshToken().toString()).enqueue(object: Callback<ResponseWrapper<TokenResult>> {
//            override fun onResponse(call: Call<ResponseWrapper<TokenResult>>, response: retrofit2.Response<ResponseWrapper<TokenResult>>) {
//                if (response.code() == 200) {
//                    val tokenResponse = response.body()
//                    if (tokenResponse?.isSuccess == true) {
//                        var newAccessToken = tokenResponse.result!!.accessToken
//                        saveAccessToken(newAccessToken)
//                    } else {
//                        Log.d("TOKEN_ERROR", "Failure")
//                    }
//                } else {
//                    removeAccessToken()
//                    removeRefreshToken()
//                }
//            }
//            override fun onFailure(call: Call<ResponseWrapper<TokenResult>>, t: Throwable) {
//                Log.e("TOKEN_ERROR", "getNewToken-FAILURE", t)
//                removeAccessToken()
//                removeRefreshToken()
//            }
//        })
//    }
}