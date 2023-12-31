package com.cmc.android_pt

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmc.android_pt.databinding.BottomSheetTopBottomTitleBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetTopBottomTitle: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetTopBottomTitleBinding
    private var topTitle: String = ""
    private var bottomTitle: String = ""
    private lateinit var dialogFinishListener: OnDialogFinishListener
    private var result: Boolean? = null

    interface OnDialogFinishListener {
        fun finish(result: Boolean?)
    }

    fun setOnDialogFinishListener(listener: OnDialogFinishListener) {
        dialogFinishListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetTopBottomTitleBinding.inflate(layoutInflater)

        topTitle = requireArguments().getString("topTitle").toString()
        bottomTitle = requireArguments().getString("bottomTitle").toString()

        binding.bottomSheetTopTitleTv.text = topTitle
        binding.bottomSheetBottomTitleTv.text = bottomTitle

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
        binding.bottomSheetTopBottomBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (binding.bottomSheetTopTitleTv.text == "비밀번호가 변경되었어요") {
            dialogFinishListener.finish(true)
        }
    }
}