package com.cmc.android.utils

import android.util.Log
import com.cmc.android.domain.attendance.req.CodeRequest
import com.cmc.android.domain.auth.TokenResult
import com.cmc.android.domain.base.ErrorResponse
import com.cmc.android.domain.base.ResponseWrapper
import com.cmc.android.network.auth.AuthInterface
import com.cmc.android.utils.ApplicationClass.Companion.Authorization_TOKEN
import com.cmc.android.utils.ApplicationClass.Companion.BASE_URL
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthorizationTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val response = chain.proceed(builder.build())

        if (getAccessToken() != null) {
            builder.addHeader(Authorization_TOKEN, "${getAccessToken()}")
        }

        if (response.code == 401) getNewToken()

        return response
    }

    private fun getNewToken() {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val getNewTokenInterface = retrofit.create(AuthInterface::class.java)
        getNewTokenInterface.getNewToken(getRefreshToken().toString()).enqueue(object: Callback<ResponseWrapper<TokenResult>> {
            override fun onResponse(call: Call<ResponseWrapper<TokenResult>>, response: retrofit2.Response<ResponseWrapper<TokenResult>>) {
                if (response.code() == 200) {
                    val tokenResponse = response.body()
                    if (tokenResponse?.isSuccess == true) {
                        var newAccessToken = tokenResponse.result!!.accessToken
                        saveAccessToken(newAccessToken)
                    } else {
                        Log.d("TOKEN_ERROR", "Failure")
                    }
                } else {
                    removeAccessToken()
                    removeRefreshToken()
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<TokenResult>>, t: Throwable) {
                Log.e("TOKEN_ERROR", "getNewToken-FAILURE", t)
                removeAccessToken()
                removeRefreshToken()
            }
        })
    }
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
//}