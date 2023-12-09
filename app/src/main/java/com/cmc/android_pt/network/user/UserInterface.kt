package com.cmc.android_pt.network.user

import com.cmc.android_pt.domain.base.ResponseWrapper
import com.cmc.android_pt.domain.req.UserInfoResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET

interface UserInterface {

    /** 내 정보 조회 */
    @GET("users")
    fun getUserInfo(): Call<ResponseWrapper<UserInfoResponse>>

    /** 유저 탈퇴 */
    @DELETE("users")
    fun deleteUser(): Call<ResponseWrapper<String>>
}