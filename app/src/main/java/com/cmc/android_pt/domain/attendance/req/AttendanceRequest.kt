package com.cmc.android_pt.domain.attendance.req

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