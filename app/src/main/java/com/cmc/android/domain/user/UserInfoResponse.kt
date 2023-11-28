package com.cmc.android.domain.req

data class UserInfoResponse(
    var name: String,
    var email: String,
    var nickname: String,
    var generation: Int,
    var part: String
)