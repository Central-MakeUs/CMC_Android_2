package com.cmc.android.network.attendances

import android.util.Log
import com.cmc.android.domain.attendance.req.AttendanceResult
import com.cmc.android.domain.base.ResponseWrapper
import com.cmc.android.domain.base.ErrorResponse
import com.cmc.android.utils.NetworkModule
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttendanceService {

    private var attendanceService = NetworkModule.getInstance()?.create(AttendanceInterface::class.java)

    private lateinit var attendanceCheckView: AttendanceCheckView

    fun setAttendanceCheckView(attendanceCheckView: AttendanceCheckView) {
        this.attendanceCheckView = attendanceCheckView
    }

    fun getAttendances() {
        attendanceService?.getAttendances()?.enqueue(object: Callback<ResponseWrapper<AttendanceResult>> {
            override fun onResponse(call: Call<ResponseWrapper<AttendanceResult>>, response: Response<ResponseWrapper<AttendanceResult>>) {
                if (response.code() == 200) {
                    val attendanceResponse = response.body()
                    if (attendanceResponse?.isSuccess == true) {
                        attendanceCheckView.attendanceCheckSuccessView(attendanceResponse.result!!)
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "getAttendances errorResponse = $errorResponse")

                    attendanceCheckView.attendanceCheckFailureView()
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<AttendanceResult>>, t: Throwable) {
                Log.d("API-ERROR", "AttendanceService_getAttendances_failure\nt = $t")
            }
        })
    }
}