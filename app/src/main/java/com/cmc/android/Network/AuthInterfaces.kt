package com.cmc.android.Network

import com.cmc.android.Data.AuthResponse
import com.cmc.android.Data.EmailResponse
import com.cmc.android.Data.LoginRequest
import com.cmc.android.Data.SignupRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthInterfaces {
    @POST("/auth/signUp")
    fun signup(
        signupRequest: SignupRequest
    ): Call<AuthResponse>

    @POST("/auth/login")
    fun login(
        loginRequest: LoginRequest
    ): Call<AuthResponse>

    @GET("/auth/email")
    fun checkEmail(
        email: String
    ): Call<EmailResponse>
}