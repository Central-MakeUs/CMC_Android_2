package com.cmc.android_pt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android_pt.R
import com.cmc.android_pt.databinding.ActivityChangePwdFirstBinding
import com.cmc.android_pt.network.auth.AuthService
import com.cmc.android_pt.network.auth.SendEmailView

class ChangePwdFirstActivity: AppCompatActivity(), SendEmailView {

    private lateinit var binding: ActivityChangePwdFirstBinding
    private var account = false
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePwdFirstBinding.inflate(layoutInflater)

        initService()
        initClickListener()
        initFocusListener()
        initChangeListener()

        setContentView(binding.root)
    }

    private fun initService() {
        authService = AuthService()
        authService.setSendEmailView(this)
    }

    private fun initClickListener() {
        binding.findPwdBackIv.setOnClickListener {
            finish()
        }

        binding.findPwdEmailBtn.setOnClickListener {
            authService.sendEmail(binding.findPwdEmailEt.text.toString())
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
                    binding.findPwdEmailBtn.setTextColor(ContextCompat.getColor(this@ChangePwdFirstActivity, R.color.gray_50))
                    binding.findPwdEmailBtn.isEnabled = true
                } else {
                    binding.findPwdEmailBtn.setBackgroundResource(R.drawable.login_round_5_x)
                    binding.findPwdEmailBtn.setTextColor(ContextCompat.getColor(this@ChangePwdFirstActivity, R.color.gray_700))
                    binding.findPwdEmailBtn.isEnabled = false
                }
            }
            override fun afterTextChanged(p0: Editable?) { }
        })
    }

    override fun sendEmailSuccessView() {
        var bottomSheetTitleContent = BottomSheetTitleContent()
        var bundle = Bundle()
        var bottomSheetTag = ""

        bundle.putString("title", "인증번호를 전송했어요")
        bundle.putString("content", "3분 내 인증번호를 입력해주세요 :)")
        bottomSheetTag = "BottomSheetSuccess"

        bottomSheetTitleContent.arguments = bundle
        bottomSheetTitleContent.show(supportFragmentManager, bottomSheetTag)
        bottomSheetTitleContent.setOnDialogFinishListener(object: BottomSheetTitleContent.OnDialogFinishListener {
            override fun finish(result: Boolean?) {
                if (result == true) {
                    var intent = Intent(this@ChangePwdFirstActivity, ChangePwdSecondActivity::class.java)
                    intent.putExtra("email", binding.findPwdEmailEt.text.toString())
                    startActivity(intent)
                }
            }
        })
    }

    override fun sendEmailFailureView() {
        var bottomSheetTitleContent = BottomSheetTitleContent()
        var bundle = Bundle()
        var bottomSheetTag = ""

        bundle.putString("title", "존재하지 않는 계정이에요")
        bundle.putString("content", "아이디 또는 비밀번호를 확인해주세요!")
        bottomSheetTag = "BottomSheetNoAccount"

        bottomSheetTitleContent.arguments = bundle
        bottomSheetTitleContent.show(supportFragmentManager, bottomSheetTag)
    }
}