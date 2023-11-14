package com.cmc.android.network

import com.cmc.android.data.AuthResult

interface LoginView {
    fun loginSuccessView(result: AuthResult)
    fun loginFailureView(message: String)
}

interface SignupView {
    fun signupSuccessView(result: AuthResult)
    fun signupFailureView(message: String)
}

interface EmailView {
    fun emailSuccessView(result: String)
    fun emailFailureView(message: String)
}