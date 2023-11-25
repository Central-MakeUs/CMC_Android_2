package com.cmc.android.domain.base

data class ErrorResponse(
    var isSuccess: Boolean,
    var code: String,
    var message: String
)