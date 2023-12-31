package com.cmc.android_pt.network.notification

import android.util.Log
import com.cmc.android_pt.domain.base.ErrorResponse
import com.cmc.android_pt.domain.base.ResponseWrapper
import com.cmc.android_pt.domain.notification.NotificationResult
import com.cmc.android_pt.utils.NetworkModule
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
        notificationService?.getNotifications()?.enqueue(object: Callback<ResponseWrapper<ArrayList<NotificationResult>>> {
                override fun onResponse(call: Call<ResponseWrapper<ArrayList<NotificationResult>>>, response: Response<ResponseWrapper<ArrayList<NotificationResult>>>) {
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
            override fun onFailure(call: Call<ResponseWrapper<ArrayList<NotificationResult>>>, t: Throwable) {
                Log.d("API-ERROR", "NotificationService_getNotifications_failure")
            }
        })
    }
}