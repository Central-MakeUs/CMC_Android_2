package com.cmc.android

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmc.android.databinding.BottomSheetLeaveUserBinding
import com.cmc.android.network.user.LeaveUserView
import com.cmc.android.network.user.UserService
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetLeaveUser: BottomSheetDialogFragment(), LeaveUserView {

    private lateinit var binding: BottomSheetLeaveUserBinding
    private lateinit var dialogFinishListener: OnDialogFinishListener
    private lateinit var userService: UserService
    private var result: Boolean = false

    interface OnDialogFinishListener {
        fun finish(result: Boolean)
    }

    fun setOnDialogFinishListener(listener: OnDialogFinishListener) {
        dialogFinishListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetLeaveUserBinding.inflate(layoutInflater)

        initService()
        initClickListener()

        return binding.root
    }

    private fun initService() {
        userService = UserService()
        userService.setLeaveUserView(this)
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
        binding.bottomSheetLeaveUserBackBtn.setOnClickListener {
            dismiss()
        }
        binding.bottomSheetLeaveUserBtn.setOnClickListener {
            userService.deleteUser()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish(result)
    }

    override fun leaveUserSuccessView() {
        result = true
    }

    override fun leaveUserFailureView() {
        result = false
    }
}