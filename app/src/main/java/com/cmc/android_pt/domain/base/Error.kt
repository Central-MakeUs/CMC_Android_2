package com.cmc.android_pt.domain.base

data class ErrorResponse(
    var isSuccess: Boolean,
    var code: String,
    var message: String
)