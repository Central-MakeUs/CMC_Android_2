package com.cmc.android_pt.network.user

import android.util.Log
import com.cmc.android_pt.domain.base.ResponseWrapper
import com.cmc.android_pt.domain.base.ErrorResponse
import com.cmc.android_pt.domain.req.UserInfoResponse
import com.cmc.android_pt.utils.NetworkModule
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {

    private var userService = NetworkModule.getInstance()?.create(UserInterface::class.java)

    private lateinit var userView: UserView
    private lateinit var leaveUserView: LeaveUserView

    fun setUserView(userView: UserView) {
        this.userView = userView
    }
    fun setLeaveUserView(leaveUserView: LeaveUserView) {
        this.leaveUserView = leaveUserView
    }

    fun getUserInfo() {
        userService?.getUserInfo()?.enqueue(object: Callback<ResponseWrapper<UserInfoResponse>> {
            override fun onResponse(call: Call<ResponseWrapper<UserInfoResponse>>, response: Response<ResponseWrapper<UserInfoResponse>>) {
                Log.d("API-TEST", "response = $response")
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

    fun deleteUser() {
        userService?.deleteUser()?.enqueue(object: Callback<ResponseWrapper<String>> {
            override fun onResponse(call: Call<ResponseWrapper<String>>, response: Response<ResponseWrapper<String>>) {
                Log.d("API-TEST", "response = $response")
                if (response.code() == 200) {
                    val userResponse = response.body()
                    if (userResponse?.isSuccess == true) {
                        leaveUserView.leaveUserSuccessView()
                    } else {
                        leaveUserView.leaveUserFailureView()
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "deleteUser errorResponse = $errorResponse")

                    leaveUserView.leaveUserFailureView()
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<String>>, t: Throwable) {
                Log.d("API-ERROR", "UserService_deleteUser_failure")
            }
        })
    }
}