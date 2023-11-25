package com.cmc.android.domain.auth.req

data class EmailAndCodeRequest(
    val code: String,
    val email: String
)