package com.cmc.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmc.android.databinding.ActivityAttendanceCheckBinding
import com.cmc.android.domain.attendance.req.AttendanceResult
import com.cmc.android.domain.attendance.req.Attendances
import com.cmc.android.network.attendances.AttendanceCheckView
import com.cmc.android.network.attendances.AttendanceService

class AttendanceCheckActivity: AppCompatActivity(), AttendanceCheckView {

    lateinit var binding: ActivityAttendanceCheckBinding
    private lateinit var attendanceService: AttendanceService
    private lateinit var attendancesRVAdapter: AttendanceRVAdapter
    private var attendancesList = arrayListOf<Attendances>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAttendanceCheckBinding.inflate(layoutInflater)

        initClickListener()
        initService()
        initAdapter()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.attendanceCheckBackIv.setOnClickListener {
            finish()
        }
    }

    private fun initService() {
        attendanceService = AttendanceService()
        attendanceService.setAttendanceCheckView(this)
        attendanceService.getAttendances()
    }

    private fun initAdapter() {
        attendancesRVAdapter = AttendanceRVAdapter(attendancesList)
        binding.attendanceListRv.adapter = attendancesRVAdapter
        binding.attendanceListRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun attendanceCheckSuccessView(result: AttendanceResult) {
        binding.attendanceCheckFirstResultTv.text = result.attendanceStatus.attendanceCount.toString()
        binding.attendanceCheckSecondResultTv.text = result.attendanceStatus.lateCount.toString()
        binding.attendanceCheckThirdResultTv.text = result.attendanceStatus.absentCount.toString()

        if (result.attandances.isNotEmpty()) {
            attendancesRVAdapter.addAllItems(result.attandances)
        }
    }

    override fun attendanceCheckFailureView() {

    }
}