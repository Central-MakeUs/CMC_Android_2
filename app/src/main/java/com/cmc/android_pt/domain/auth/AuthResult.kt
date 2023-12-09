package com.cmc.android_pt.domain.auth

data class AuthResult(
    var userId: Int,
    var accessToken: String,
    var refreshToken: String
)