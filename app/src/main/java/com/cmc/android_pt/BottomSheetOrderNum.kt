package com.cmc.android_pt

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmc.android_pt.databinding.BottomSheetGenerationBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetGeneration: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetGenerationBinding
    private lateinit var dialogFinishListener: OnDialogFinishListener
    private var generation: String = ""

    interface OnDialogFinishListener {
        fun finish(generation: String)
    }

    fun setOnDialogFinishListener(listener: OnDialogFinishListener) {
        dialogFinishListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetGenerationBinding.inflate(layoutInflater)

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
            generation = "14기"
            dismiss()
        }
        binding.bottomSheet13thCl.setOnClickListener {
            generation = "13기"
            dismiss()
        }
        binding.bottomSheet12thCl.setOnClickListener {
            generation = "12기"
            dismiss()
        }
        binding.bottomSheet11thCl.setOnClickListener {
            generation = "11기"
            dismiss()
        }

        binding.bottomSheetGenerationCancelBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFinishListener.finish(generation)
    }
}