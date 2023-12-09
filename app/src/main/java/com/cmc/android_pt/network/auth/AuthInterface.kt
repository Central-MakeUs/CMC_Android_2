package com.cmc.android_pt.network.auth

import com.cmc.android_pt.domain.base.ResponseWrapper
import com.cmc.android_pt.domain.auth.AuthResult
import com.cmc.android_pt.domain.auth.TokenResult
import com.cmc.android_pt.domain.auth.req.EmailAndCodeRequest
import com.cmc.android_pt.domain.auth.req.LoginRequest
import com.cmc.android_pt.domain.auth.req.SignupRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthInterface {

    /** 로그인 */
    @POST("auth/log-in")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<ResponseWrapper<AuthResult>>

    /** 회원가입 */
    @POST("/auth/sign-up")
    fun signup(
        @Body signupRequest: SignupRequest
    ): Call<ResponseWrapper<AuthResult>>

    /** 이메일 중복체크 */
    @GET("/auth/email")
    fun checkEmail(
        @Query("email") email : String
    ): Call<ResponseWrapper<String>>

    /** 비밀번호 찾기용 이메일 인증번호 전송 */
    @GET("/auth/password")
    fun sendEmailPassword(
        @Query("email") email : String
    ): Call<ResponseWrapper<String>>

    /** 이메일 인증번호 확인 */
    @POST("/auth/password")
    fun checkEmailValidate(
        @Body emailAndCodeRequest: EmailAndCodeRequest
    ): Call<ResponseWrapper<String>>

    /** 비밀번호 변경 */
    @PATCH("/auth/password")
    fun changePassword(
        @Body loginRequest: LoginRequest
    ): Call<ResponseWrapper<String>>

    /** 새로운 토큰 발급 */
    @GET("/auth/refresh")
    fun getNewToken(
        @Header("X-REFRESH-TOKEN") refreshToken: String
    ): Call<ResponseWrapper<TokenResult>>
}