package com.cmc.android_pt.network.attendances

import com.cmc.android_pt.domain.attendance.req.AttendanceResult

interface AttendanceCheckView {
    fun attendanceCheckSuccessView(result: AttendanceResult)
    fun attendanceCheckFailureView()
}

interface AttendanceSendView {
    fun attendanceSendSuccessView()
    fun attendanceSendFailureView()
}