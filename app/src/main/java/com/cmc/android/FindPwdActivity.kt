package com.cmc.android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android.databinding.ActivityFindPwdBinding
import java.util.Timer

class FindPwdActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFindPwdBinding
    private var account = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPwdBinding.inflate(layoutInflater)

        initClickListener()
        initFocusListener()
        initChangeListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.findPwdEmailBtn.setOnClickListener {
            // UPDATE: API 연동 후 수정
            var bottomSheetTitleContent = BottomSheetTitleContent()
            var bundle = Bundle()
            var bottomSheetTag = ""

            if (account) {
                // MEMO: If 계정이 있다면
                bundle.putString("title", "인증번호를 전송했어요")
                bundle.putString("content", "3분 내 인증번호를 입력해주세요 :)")
                bottomSheetTag = "BottomSheetSuccess"
            } else {
                // MEMO: If 계정이 없다면
                bundle.putString("title", "존재하지 않는 계정이에요")
                bundle.putString("content", "아이디 또는 비밀번호를 확인해주세요!")
                bottomSheetTag = "BottomSheetNoAccount"
            }

            bottomSheetTitleContent.arguments = bundle
            bottomSheetTitleContent.show(supportFragmentManager, bottomSheetTag)
            bottomSheetTitleContent.setOnDialogFinishListener(object: BottomSheetTitleContent.OnDialogFinishListener {
                override fun finish(result: Boolean?) {
                    if (result == true) {
                        var intent = Intent(this@FindPwdActivity, FindPwdNumberActivity::class.java)
                        startActivity(intent)
                    }
                }
            })

            // UPDATE: 아래 부분 삭제하기!
            account = !account
        }
    }

    private fun initFocusListener() {
        binding.findPwdEmailEt.setOnFocusChangeListener { _, focus ->
            if (focus) {
                binding.findPwdEmailLine.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_200))
            } else {
                binding.findPwdEmailLine.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_700))
            }
        }
    }

    private fun initChangeListener() {
        binding.findPwdEmailEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.findPwdEmailEt.text.isNotEmpty()) {
                    binding.findPwdEmailBtn.setBackgroundResource(R.drawable.login_round_5_o)
                    binding.findPwdEmailBtn.setTextColor(ContextCompat.getColor(this@FindPwdActivity, R.color.gray_50))
                    binding.findPwdEmailBtn.isEnabled = true
                } else {
                    binding.findPwdEmailBtn.setBackgroundResource(R.drawable.login_round_5_x)
                    binding.findPwdEmailBtn.setTextColor(ContextCompat.getColor(this@FindPwdActivity, R.color.gray_700))
                    binding.findPwdEmailBtn.isEnabled = false
                }
            }
            override fun afterTextChanged(p0: Editable?) { }
        })
    }
}