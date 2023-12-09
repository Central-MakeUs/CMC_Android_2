package com.cmc.android_pt.network.attendances

import android.util.Log
import com.cmc.android_pt.domain.attendance.req.AttendanceResult
import com.cmc.android_pt.domain.attendance.req.CodeRequest
import com.cmc.android_pt.domain.base.ResponseWrapper
import com.cmc.android_pt.domain.base.ErrorResponse
import com.cmc.android_pt.utils.NetworkModule
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttendanceService {

    private var attendanceService = NetworkModule.getInstance()?.create(AttendanceInterface::class.java)

    private lateinit var attendanceCheckView: AttendanceCheckView
    private lateinit var attendanceSendView: AttendanceSendView

    fun setAttendanceCheckView(attendanceCheckView: AttendanceCheckView) {
        this.attendanceCheckView = attendanceCheckView
    }
    fun setAttendanceSendView(attendanceSendView: AttendanceSendView) {
        this.attendanceSendView = attendanceSendView
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

    fun sendAttendance(code: String) {
        attendanceService?.postAttendances(CodeRequest(code))?.enqueue(object: Callback<ResponseWrapper<String>> {
            override fun onResponse(call: Call<ResponseWrapper<String>>, response: Response<ResponseWrapper<String>>) {
                Log.d("API-TEST", "response = $response")
                if (response.code() == 200) {
                    val attendanceResponse = response.body()
                    if (attendanceResponse?.isSuccess == true) {
                        attendanceSendView.attendanceSendSuccessView()
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    Log.d("API-ERROR", "sendAttendance errorResponse = $errorResponse")

                    attendanceSendView.attendanceSendFailureView()
                }
            }
            override fun onFailure(call: Call<ResponseWrapper<String>>, t: Throwable) {
                Log.d("API-ERROR", "AttendanceService_sendAttendance_failure\nt = $t")
            }
        })
    }
}