package com.cmc.android

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmc.android.databinding.BottomSheetTitleContentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetTitleContent: BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetTitleContentBinding
    private var title: String = ""
    private var content: String = ""
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
        binding = BottomSheetTitleContentBinding.inflate(layoutInflater)

        title = requireArguments().getString("title").toString()
        content = requireArguments().getString("content").toString()

        binding.bottomSheetTitleTv.text = title
        binding.bottomSheetContentTv.text = content

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
        binding.bottomSheetTitleContentBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (binding.bottomSheetTitleTv.text == "인증번호를 전송했어요") {
            dialogFinishListener.finish(true)
        }
    }
}