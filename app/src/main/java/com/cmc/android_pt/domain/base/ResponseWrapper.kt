package com.cmc.android_pt.domain.base

data class ResponseWrapper <T> (
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: T?
)
