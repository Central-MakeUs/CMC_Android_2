package com.cmc.android.data

data class LoginRequest(
    var email: String,
    var password: String
)

data class SignupRequest(
    var email: String,
    var password: String,
    var nickname: String,
    var name: String,
    var generation: Int,
    var part: String
)

data class AuthResponse(
    var isSuccess: Boolean,
    var code: String,
    var message: String,
    var result: AuthResult?
)

data class AuthResult(
    var userId: Int,
    var accessToken: String,
    var refreshToken: String
)

data class EmailResponse(
    var isSuccess: Boolean,
    var code: String,
    var message: String,
    var result: String?
)