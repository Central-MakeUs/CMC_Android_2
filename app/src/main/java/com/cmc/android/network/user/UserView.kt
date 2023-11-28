package com.cmc.android.network.user

import com.cmc.android.domain.req.UserInfoResponse

interface UserView {
    fun getUserInfoSuccessView(result: UserInfoResponse)
    fun getUserInfoFailureView()
}

interface LeaveUserView {
    fun leaveUserSuccessView()
    fun leaveUserFailureView()
}