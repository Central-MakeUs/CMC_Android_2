package com.cmc.android

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmc.android.databinding.BottomSheetOrderNumBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOrderNum: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetOrderNumBinding
    private lateinit var dialogFinishListener: OnDialogFinishListener
    private var orderNum: String = ""

    interface OnDialogFinishListener {
        fun finish(orderNum: String)
    }

    fun setOnDialogFinishListener(listener: OnDialogFinishListener) {
        dialogFinishListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetOrderNumBinding.inflate(layoutInflater)

        initClickListener()

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
        binding.bottomSheet14thCl.setOnClickListener {
            orderNum = "14기"
            dismiss()
        }
        binding.bottomSheet13thCl.setOnClickListener {
            orderNum = "13기"
            dismiss()
        }
        binding.bottomSheet12thCl.setOnClickListener {
            orderNum = "12기"
            dismiss()
        }
        binding.bottomSheet11thCl.setOnClickListener {
            orderNum = "11기"
            dismiss()
        }

        binding.bottomSheetOrderNumCancelBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish(orderNum)
    }
}