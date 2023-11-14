package com.cmc.android.network

import com.cmc.android.data.AuthResponse
import com.cmc.android.data.EmailResponse
import com.cmc.android.data.LoginRequest
import com.cmc.android.data.SignupRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthInterfaces {
    @POST("auth/logIn")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<AuthResponse>

    @POST("/auth/signup")
    fun signup(
        @Body signupRequest: SignupRequest
    ): Call<AuthResponse>

    @GET("/auth/email")
    fun checkEmail(
        email: String
    ): Call<EmailResponse>
}