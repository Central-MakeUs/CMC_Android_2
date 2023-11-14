package com.cmc.android.network

import android.util.Log
import com.cmc.android.data.AuthResponse
import com.cmc.android.data.EmailResponse
import com.cmc.android.data.ErrorResponse
import com.cmc.android.data.LoginRequest
import com.cmc.android.data.SignupRequest
import com.cmc.android.utils.NetworkModule
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {

    private var authService = NetworkModule.getInstance()?.create(AuthInterfaces::class.java)

    private lateinit var loginView: LoginView
    private lateinit var signupView: SignupView
    private lateinit var emailView: EmailView

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }
    fun setSignupView(signupView: SignupView) {
        this.signupView = signupView
    }
    fun setEmailView(emailView: EmailView) {
        this.emailView = emailView
    }

    fun login(loginRequest: LoginRequest) {
        authService?.login(loginRequest)?.enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.code() == 200) {
                    val authResponse = response.body()
                    if (authResponse?.isSuccess == true) {
                        loginView.loginSuccessView(authResponse.result!!)
                    } else {
                        loginView.loginFailureView(response.body()!!.message)
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "errorResponse = $errorResponse")
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("API-ERROR", "AuthService_login_failure")
            }
        })
    }

    fun signup(signupRequest: SignupRequest) {
        authService?.signup(signupRequest)?.enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.code() == 200) {
                    val authResponse = response.body()
                    if (authResponse?.isSuccess == true) {
                        signupView.signupSuccessView(authResponse.result!!)
                    } else {
                        signupView.signupFailureView(response.body()!!.message)
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "errorResponse = $errorResponse")
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("API-ERROR", "AuthService_signup_failure")
            }
        })
    }

    fun checkEmail(email: String) {
        authService?.checkEmail(email)?.enqueue(object: Callback<EmailResponse> {
            override fun onResponse(call: Call<EmailResponse>, response: Response<EmailResponse>) {
                if (response.code() == 200) {
                    val emailResponse = response.body()
                    if (emailResponse?.isSuccess == true) {
                        emailView.emailSuccessView(emailResponse.result!!)
                    } else {
                        emailView.emailFailureView(response.body()!!.message)
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "errorResponse = $errorResponse")
                }
            }
            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                Log.d("API-ERROR", "AuthService_email_failure")
            }
        })
    }
}