package com.cmc.android.domain.attendance.req

import com.cmc.android.domain.attendance.AttendanceHour
import com.cmc.android.domain.attendance.AttendanceHourStatus

data class AttendanceResult(
    var attendanceStatus: AttendanceStatus,
    var attandances: ArrayList<Attendances>
)

data class AttendanceStatus(
    var attendanceCount: Int,
    var lateCount: Int,
    var absentCount: Int
)

data class Attendances(
    var week: Int,
    var firstHour: String,
    var secondHour: String,
    var isOffline: Boolean,
    var enable: Boolean,
    var date: String
)