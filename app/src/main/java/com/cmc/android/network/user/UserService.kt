package com.cmc.android.network.user

import android.util.Log
import com.cmc.android.domain.base.ResponseWrapper
import com.cmc.android.domain.base.ErrorResponse
import com.cmc.android.domain.req.UserInfoResponse
import com.cmc.android.utils.NetworkModule
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {

    private var userService = NetworkModule.getInstance()?.create(UserInterface::class.java)

    private lateinit var userView: UserView

    fun setUserView(userView: UserView) {
        this.userView = userView
    }

    fun getUserInfo() {
        userService?.getUserInfo()?.enqueue(object: Callback<ResponseWrapper<UserInfoResponse>> {
            override fun onResponse(call: Call<ResponseWrapper<UserInfoResponse>>, response: Response<ResponseWrapper<UserInfoResponse>>) {
                if (response.code() == 200) {
                    val userResponse = response.body()
                    if (userResponse?.isSuccess == true) {
                        userView.getUserInfoSuccessView(userResponse.result!!)
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "getUserInfo errorResponse = $errorResponse")

                    userView.getUserInfoFailureView()
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<UserInfoResponse>>, t: Throwable) {
                Log.d("API-ERROR", "UserService_getUserInfo_failure")
            }
        })
    }
}