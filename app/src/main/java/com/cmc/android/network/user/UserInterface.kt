package com.cmc.android.network.user

import com.cmc.android.domain.base.ResponseWrapper
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET

interface UserInterface {

    /** 내 정보 조회 */
    @GET("users")
    fun getUserInfo(): Call<ResponseWrapper<String>>

    /** 유저 탈퇴 */
    @DELETE("users")
    fun deleteUser(): Call<ResponseWrapper<String>>

}