package com.cmc.android

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmc.android.databinding.BottomSheetPartBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetPart: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetPartBinding
    private lateinit var dialogFinishListener: OnDialogFinishListener
    private var part: String = ""

    interface OnDialogFinishListener {
        fun finish(part: String)
    }

    fun setOnDialogFinishListener(listener: OnDialogFinishListener) {
        dialogFinishListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetPartBinding.inflate(layoutInflater)

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
        binding.bottomSheetPlanCl.setOnClickListener {
            part = "Plan"
            dismiss()
        }
        binding.bottomSheetDesignCl.setOnClickListener {
            part = "Design"
            dismiss()
        }
        binding.bottomSheetWebCl.setOnClickListener {
            part = "Web"
            dismiss()
        }
        binding.bottomSheetIosCl.setOnClickListener {
            part = "iOS"
            dismiss()
        }
        binding.bottomSheetAndroidCl.setOnClickListener {
            part = "Android"
            dismiss()
        }
        binding.bottomSheetServerCl.setOnClickListener {
            part = "Server"
            dismiss()
        }

        binding.bottomSheetPartCancelBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish(part)
    }
}