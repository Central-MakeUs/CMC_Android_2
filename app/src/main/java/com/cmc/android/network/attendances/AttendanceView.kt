package com.cmc.android.network.attendances

import com.cmc.android.domain.attendance.req.AttendanceResult

interface AttendanceCheckView {
    fun attendanceCheckSuccessView(result: AttendanceResult)
    fun attendanceCheckFailureView()
}

interface AttendanceSendView {
    fun attendanceSendSuccessView()
    fun attendanceSendFailureView()
}