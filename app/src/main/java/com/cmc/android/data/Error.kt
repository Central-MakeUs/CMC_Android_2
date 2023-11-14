package com.cmc.android.data

data class ErrorResponse(
    var isSuccess: Boolean,
    var code: String,
    var message: String
)