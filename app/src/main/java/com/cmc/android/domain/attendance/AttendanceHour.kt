package com.cmc.android.domain.attendance

enum class AttendanceHour(
    val value: String,
    val hour: Int
) {
    FIRST_HOUR("FIRST HOUR", 1),
    SECOND_HOUR("SECOND HOUR", 2);

    companion object {
        fun of(intHour: Int): AttendanceHour {
            return when (intHour) {
                1 -> FIRST_HOUR
                2 -> SECOND_HOUR
                else -> throw IllegalArgumentException("해당 Hour은 존재하지 않습니다.")
            }
        }
    }
}