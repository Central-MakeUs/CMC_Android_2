package com.cmc.android_pt.network.notification

import com.cmc.android_pt.domain.notification.NotificationResult

interface NotificationView {
    fun getNotificationSuccessView(result: ArrayList<NotificationResult>)
    fun getNotificationFailureView()
}