package com.cmc.android

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.cmc.android.databinding.BottomSheetNumBinding
import com.cmc.android.network.attendances.AttendanceSendView
import com.cmc.android.network.attendances.AttendanceService
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetNum: BottomSheetDialogFragment(), AttendanceSendView {

    private lateinit var binding: BottomSheetNumBinding
    private lateinit var dialogFinishListener: OnDialogFinishListener
    private var result: Boolean = false
    private lateinit var attendanceService: AttendanceService

    interface OnDialogFinishListener {
        fun finish(result: Boolean)
    }

    fun setOnDialogFinishListener(listener: OnDialogFinishListener) {
        dialogFinishListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetNumBinding.inflate(layoutInflater)

        initClickListener()
        initService()

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun initClickListener() {
        binding.bottomSheetNumNextBtn.setOnClickListener {
            attendanceService.sendAttendance(binding.bottomSheetNumEt.text.toString())
        }
    }

    private fun initService() {
        attendanceService = AttendanceService()
        attendanceService.setAttendanceSendView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish(result)
    }

    override fun attendanceSendSuccessView() {
        result = true
        binding.bottomSheetNumResultTv.visibility = View.INVISIBLE
        binding.bottomSheetNumLine.setBackgroundColor(Color.parseColor("#FF7B7F83"))
        dismiss()
    }

    override fun attendanceSendFailureView() {
        result = false
        binding.bottomSheetNumResultTv.visibility = View.VISIBLE
        binding.bottomSheetNumLine.setBackgroundColor(Color.parseColor("#FFFF2633"))
    }
}