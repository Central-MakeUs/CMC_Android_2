package com.cmc.android.network.notification

import com.cmc.android.domain.notification.NotificationResult

interface NotificationView {
    fun getNotificationSuccessView(result: NotificationResult)
    fun getNotificationFailureView()
}