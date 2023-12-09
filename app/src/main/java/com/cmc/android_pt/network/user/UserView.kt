package com.cmc.android_pt.network.user

import com.cmc.android_pt.domain.req.UserInfoResponse

interface UserView {
    fun getUserInfoSuccessView(result: UserInfoResponse)
    fun getUserInfoFailureView()
}

interface LeaveUserView {
    fun leaveUserSuccessView()
    fun leaveUserFailureView()
}