package com.cmc.android.domain.auth

data class AuthResult(
    var userId: Int,
    var accessToken: String,
    var refreshToken: String
)