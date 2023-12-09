package com.cmc.android_pt.network.notification

import com.cmc.android_pt.domain.base.ResponseWrapper
import com.cmc.android_pt.domain.notification.NotificationResult
import retrofit2.Call
import retrofit2.http.GET

interface NotificationInterface {

    /** 공지사항 조회 */
    @GET("notifications/latest")
    fun getNotifications(): Call<ResponseWrapper<ArrayList<NotificationResult>>>
}