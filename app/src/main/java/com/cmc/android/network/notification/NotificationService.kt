package com.cmc.android.network.notification

import android.util.Log
import com.cmc.android.domain.base.ErrorResponse
import com.cmc.android.domain.base.ResponseWrapper
import com.cmc.android.domain.notification.NotificationResult
import com.cmc.android.utils.NetworkModule
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationService {

    private var notificationService = NetworkModule.getInstance()?.create(NotificationInterface::class.java)

    private lateinit var notificationView: NotificationView

    fun setNotificationView(notificationView: NotificationView) {
        this.notificationView = notificationView
    }

    fun getNotifications() {
        notificationService?.getNotifications()?.enqueue(object: Callback<ResponseWrapper<NotificationResult>> {
            override fun onResponse(call: Call<ResponseWrapper<NotificationResult>>, response: Response<ResponseWrapper<NotificationResult>>) {
                Log.d("API-TEST", "response = $response")

                if (response.code() == 200) {
                    val notificationResponse = response.body()
                    if (notificationResponse?.isSuccess == true) {
                        notificationView.getNotificationSuccessView(notificationResponse.result!!)
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "getUserInfo errorResponse = $errorResponse")

                    notificationView.getNotificationFailureView()
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<NotificationResult>>, t: Throwable) {
                Log.d("API-ERROR", "NotificationService_getNotifications_failure")
            }
        })
    }
}