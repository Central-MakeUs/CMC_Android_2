package com.cmc.android_pt

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cmc.android_pt.R
import com.cmc.android_pt.databinding.ItemAttendanceBinding
import com.cmc.android_pt.domain.attendance.req.Attendances

class AttendanceRVAdapter(private val attendanceList: ArrayList<Attendances>): RecyclerView.Adapter<AttendanceRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceRVAdapter.ViewHolder {
        val binding: ItemAttendanceBinding = ItemAttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = attendanceList.size

    override fun onBindViewHolder(holder: AttendanceRVAdapter.ViewHolder, position: Int) {
        holder.bind(attendanceList[position])
    }

    inner class ViewHolder(val binding: ItemAttendanceBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(attendances: Attendances) {
            binding.itemAttendanceWeekTv.text = "${attendances.week}주차"
            binding.itemAttendanceTimeTv.text = "${attendances.date.split("-")[1]}.${attendances.date.split("-")[2]}"

            if (attendances.isOffline) {
                binding.itemAttendanceIsOfflineTv.text = "Offline"
            } else binding.itemAttendanceIsOfflineTv.text = "Online"

            if (attendances.firstHour == "ABSENT") {
                setAttendanceImage(binding.itemAttendanceFirstIv, 3)
            } else if (attendances.firstHour == "LATE") {
                setAttendanceImage(binding.itemAttendanceFirstIv, 2)
            } else {
                setAttendanceImage(binding.itemAttendanceFirstIv, 1)
            }

            if (attendances.secondHour == "ABSENT") {
                setAttendanceImage(binding.itemAttendanceSecondIv, 3)
            } else if (attendances.secondHour == "LATE") {
                setAttendanceImage(binding.itemAttendanceSecondIv, 2)
            } else {
                setAttendanceImage(binding.itemAttendanceSecondIv, 1)
            }
        }
    }

    private fun setAttendanceImage(imageView: ImageView, flag: Int) {
        // MEMO: flag 1 출석 / flag 2 지각 / flag 3 결석
        if (flag == 1 || flag == 2) imageView.setImageResource(R.drawable.ic_attendance_o)
        else if (flag == 3) imageView.setImageResource(R.drawable.ic_attendance_x)
    }

    fun addAllItems(items: ArrayList<Attendances>) {
        attendanceList.addAll(items)
        this.notifyDataSetChanged()
    }
}