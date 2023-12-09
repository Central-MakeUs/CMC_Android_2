package com.cmc.android_pt.network.attendances

import com.cmc.android_pt.domain.attendance.req.AttendanceResult
import com.cmc.android_pt.domain.attendance.req.CodeRequest
import com.cmc.android_pt.domain.base.ResponseWrapper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AttendanceInterface {

    /** 출석 현황 조회 */
    @GET("attendances")
    fun getAttendances(): Call<ResponseWrapper<AttendanceResult>>

    /** 출석 체크 진행 */
    @POST("attendances")
    fun postAttendances(
        @Body code: CodeRequest
    ): Call<ResponseWrapper<String>>
}