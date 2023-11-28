package com.cmc.android.domain.auth.req

data class EmailAndCodeRequest(
    var email: String,
    var code: String
)