package com.cmc.android.domain.base

data class ResponseWrapper <T> (
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: T?
)
