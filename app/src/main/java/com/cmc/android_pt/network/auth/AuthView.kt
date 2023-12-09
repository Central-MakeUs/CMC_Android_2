package com.cmc.android_pt.network.auth

import com.cmc.android_pt.domain.auth.AuthResult
import com.cmc.android_pt.domain.auth.TokenResult

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

interface ChangePasswordView {
    fun changePasswordSuccessView()
    fun changePasswordFailureView()
}

interface GetNewTokenView {
    fun getNewTokenSuccessView(result: TokenResult)
    fun getNewTokenFailureView()
}