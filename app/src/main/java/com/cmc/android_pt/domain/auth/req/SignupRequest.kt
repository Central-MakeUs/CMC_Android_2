package com.cmc.android_pt.domain.auth.req

data class SignupRequest(
    var email: String,
    var password: String,
    var nickname: String,
    var name: String,
    var generation: Int,
    var part: String
)