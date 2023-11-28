package com.cmc.android.network.auth

import com.cmc.android.domain.auth.AuthResult

interface LoginView {
    fun loginSuccessView(result: AuthResult)
    fun loginFailureView()
}

interface SignupView {
    fun signupSuccessView(result: AuthResult)
    fun signupFailureView(message: String)
}

interface EmailView {
    fun emailSuccessView(result: String)
    fun emailFailureView(message: String)
}

interface SendEmailView {
    fun sendEmailSuccessView()
    fun sendEmailFailureView()
}

interface CheckEmailValidationView {
    fun checkEmailValidationSuccessView()
    fun checkEmailValidationFailureView()
}