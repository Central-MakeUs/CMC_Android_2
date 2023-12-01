package com.cmc.android.network.auth

import android.util.Log
import com.cmc.android.domain.base.ResponseWrapper
import com.cmc.android.domain.auth.AuthResult
import com.cmc.android.domain.auth.TokenResult
import com.cmc.android.domain.auth.req.EmailAndCodeRequest
import com.cmc.android.domain.auth.req.SignupRequest
import com.cmc.android.domain.base.ErrorResponse
import com.cmc.android.domain.auth.req.LoginRequest
import com.cmc.android.utils.NetworkModule
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {

    private var authService = NetworkModule.getInstance()?.create(AuthInterface::class.java)

    private lateinit var loginView: LoginView
    private lateinit var signupView: SignupView
    private lateinit var emailView: EmailView
    private lateinit var sendEmailView: SendEmailView
    private lateinit var checkEmailValidationView: CheckEmailValidationView
    private lateinit var changePasswordView: ChangePasswordView
    private lateinit var getNewTokenView: GetNewTokenView

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }
    fun setSignupView(signupView: SignupView) {
        this.signupView = signupView
    }
    fun setEmailView(emailView: EmailView) {
        this.emailView = emailView
    }
    fun setSendEmailView(sendEmailView: SendEmailView) {
        this.sendEmailView = sendEmailView
    }
    fun setCheckEmailValidationView(checkEmailValidationView: CheckEmailValidationView) {
        this.checkEmailValidationView = checkEmailValidationView
    }
    fun setChangePasswordView(changePasswordView: ChangePasswordView) {
        this.changePasswordView = changePasswordView
    }
    fun setGetNewTokenView(getNewTokenView: GetNewTokenView) {
        this.getNewTokenView = getNewTokenView
    }

    fun login(loginRequest: LoginRequest) {
        authService?.login(loginRequest)?.enqueue(object: Callback<ResponseWrapper<AuthResult>> {
            override fun onResponse(call: Call<ResponseWrapper<AuthResult>>, response: Response<ResponseWrapper<AuthResult>>) {
                if (response.code() == 200) {
                    val authResponse = response.body()
                    if (authResponse?.isSuccess == true) {
                        loginView.loginSuccessView(authResponse.result!!)
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "login errorResponse = $errorResponse")

                    loginView.loginFailureView()
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<AuthResult>>, t: Throwable) {
                Log.d("API-ERROR", "AuthService_login_failure")
            }
        })
    }

    fun signup(signupRequest: SignupRequest) {
        authService?.signup(signupRequest)?.enqueue(object: Callback<ResponseWrapper<AuthResult>> {
            override fun onResponse(call: Call<ResponseWrapper<AuthResult>>, response: Response<ResponseWrapper<AuthResult>>) {
                if (response.code() == 200) {
                    val authResponse = response.body()
                    if (authResponse?.isSuccess == true) {
                        signupView.signupSuccessView(authResponse.result!!)
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "signup errorResponse = $errorResponse")

                    signupView.signupFailureView(response.body()!!.message)
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<AuthResult>>, t: Throwable) {
                Log.d("API-ERROR", "AuthService_signup_failure")
            }
        })
    }

    fun checkEmail(email: String) {
        authService?.checkEmail(email)?.enqueue(object: Callback<ResponseWrapper<String>> {
            override fun onResponse(call: Call<ResponseWrapper<String>>, response: Response<ResponseWrapper<String>>) {
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
                    Log.d("API-ERROR", "checkEmail errorResponse = $errorResponse")
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<String>>, t: Throwable) {
                Log.d("API-ERROR", "AuthService_email_failure")
            }
        })
    }

    fun sendEmail(email: String) {
        authService?.sendEmailPassword(email)?.enqueue(object: Callback<ResponseWrapper<String>> {
            override fun onResponse(call: Call<ResponseWrapper<String>>, response: Response<ResponseWrapper<String>>) {
                if (response.code() == 200) {
                    val emailResponse = response.body()
                    if (emailResponse?.isSuccess == true) {
                        sendEmailView.sendEmailSuccessView()
                    } else {
                        sendEmailView.sendEmailFailureView()
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "sendEmail errorResponse = $errorResponse")
                    sendEmailView.sendEmailFailureView()
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<String>>, t: Throwable) {
                Log.d("API-ERROR", "AuthService_send_email_failure")
            }
        })
    }

    fun checkEmailValidation(email: String, code: String) {
        authService?.checkEmailValidate(EmailAndCodeRequest(email, code))?.enqueue(object: Callback<ResponseWrapper<String>> {
            override fun onResponse(call: Call<ResponseWrapper<String>>, response: Response<ResponseWrapper<String>>) {
                if (response.code() == 200) {
                    val emailResponse = response.body()
                    if (emailResponse?.isSuccess == true) {
                        checkEmailValidationView.checkEmailValidationSuccessView()
                    } else {
                        checkEmailValidationView.checkEmailValidationFailureView()
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "checkEmailValidation errorResponse = $errorResponse")
                    checkEmailValidationView.checkEmailValidationFailureView()
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<String>>, t: Throwable) {
                Log.d("API-ERROR", "AuthService_checkEmailValidation_failure")
            }
        })
    }

    fun changePassword(email: String, password: String) {
        authService?.changePassword(LoginRequest(email, password))?.enqueue(object: Callback<ResponseWrapper<String>> {
            override fun onResponse(call: Call<ResponseWrapper<String>>, response: Response<ResponseWrapper<String>>) {
                if (response.code() == 200) {
                    val changePasswordResponse = response.body()
                    if (changePasswordResponse?.isSuccess == true) {
                        changePasswordView.changePasswordSuccessView()
                    } else {
                        changePasswordView.changePasswordFailureView()
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "changePassword errorResponse = $errorResponse")
                    changePasswordView.changePasswordFailureView()
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<String>>, t: Throwable) {
                Log.d("API-ERROR", "AuthService_changePassword_failure")
            }
        })
    }

    fun getNewToken(refreshToken: String) {
        authService?.getNewToken(refreshToken)?.enqueue(object: Callback<ResponseWrapper<TokenResult>> {
            override fun onResponse(call: Call<ResponseWrapper<TokenResult>>, response: Response<ResponseWrapper<TokenResult>>) {
                if (response.code() == 200) {
                    val getNewTokenResponse = response.body()
                    if (getNewTokenResponse?.isSuccess == true) {
                        getNewTokenView.getNewTokenSuccessView(getNewTokenResponse.result!!)
                    } else {
                        getNewTokenView.getNewTokenFailureView()
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "getNewToken errorResponse = $errorResponse")
                    getNewTokenView.getNewTokenFailureView()
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<TokenResult>>, t: Throwable) {
                Log.d("API-ERROR", "AuthService_getNewToken_failure")
            }
        })
    }
}