package com.cmc.android.Network

import android.util.Log
import com.cmc.android.Data.AuthResponse
import com.cmc.android.Data.EmailResponse
import com.cmc.android.Data.LoginRequest
import com.cmc.android.Data.SignupRequest
import com.cmc.android.Utils.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {

    private var authService = NetworkModule.getInstance()?.create(AuthInterfaces::class.java)

    private lateinit var loginView: LoginView
    private lateinit var signupView: SignupView
    private lateinit var emailView: EmailView

    fun login(loginRequest: LoginRequest) {
        authService?.login(loginRequest)?.enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse?.isSuccess == true) {
                        loginView.loginSuccessView(authResponse.result!!)
                    } else {
                        loginView.loginFailureView(response.body()!!.message)
                    }
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
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse?.isSuccess == true) {
                        signupView.signupSuccessView(authResponse.result!!)
                    } else {
                        signupView.signupFailureView(response.body()!!.message)
                    }
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
                if (response.isSuccessful) {
                    val emailResponse = response.body()
                    if (emailResponse?.isSuccess == true) {
                        emailView.emailSuccessView(emailResponse.result!!)
                    } else {
                        emailView.emailFailureView(response.body()!!.message)
                    }
                }
            }
            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                Log.d("API-ERROR", "AuthService_email_failure")
            }
        })
    }
}