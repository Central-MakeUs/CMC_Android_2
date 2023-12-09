package com.cmc.android_pt.domain.auth.req

data class EmailAndCodeRequest(
    var email: String,
    var code: String
)