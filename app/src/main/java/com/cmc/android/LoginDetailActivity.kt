package com.cmc.android

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android.databinding.ActivityLoginDetailBinding


class LoginDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginDetailBinding
    private var pwdMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginDetailBinding.inflate(layoutInflater)

        initClickListener()
        initFocusListener()
        initChangeListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.loginDetailPwdModeIv.setOnClickListener {
            pwdMode = !pwdMode

            var pwdModeIc = if (pwdMode) R.drawable.ic_show else R.drawable.ic_hide
            var transformationMethod = if (pwdMode) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()

            binding.loginDetailPwdModeIv.setImageResource(pwdModeIc)
            binding.loginDetailPwdEt.transformationMethod = transformationMethod
            binding.loginDetailPwdEt.setSelection(binding.loginDetailPwdEt.text.length)
        }

        binding.loginDetailFindIdTv.setOnClickListener {
            var bottomSheetFindId = BottomSheetFindId()
            var bundle = Bundle()
            bundle.putString("topTitle", "아이디 찾기는")
            bundle.putString("bottomTitle", "운영진에게 문의해주세요 :)")
            bottomSheetFindId.arguments = bundle
            bottomSheetFindId.show(supportFragmentManager, "BottomSheetFindId")
        }

        binding.loginDetailLoginBtn.setOnClickListener {
            // UPDATE: API 연동 후 변경
            var bottomSheetLoginFail = BottomSheetLoginFail()
            var bundle = Bundle()
            bundle.putString("title", "존재하지 않는 계정이에요")
            bundle.putString("content", "아이디 또는 비밀번호를 확인해주세요!")
            bottomSheetLoginFail.arguments = bundle
            bottomSheetLoginFail.show(supportFragmentManager, "BottomSheetLoginFail")
        }
    }

    private fun initFocusListener() {
        binding.loginDetailEmailEt.setOnFocusChangeListener { _, focus ->
            if (focus) {
                binding.loginDetailEmailLine.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_200))
            } else {
                binding.loginDetailEmailLine.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_700))
            }
        }

        binding.loginDetailPwdEt.setOnFocusChangeListener { _, focus ->
            if (focus) {
                binding.loginDetailPwdLine.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_200))
            } else {
                binding.loginDetailPwdLine.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_700))
            }
        }
    }

    private fun initChangeListener() {
        binding.loginDetailEmailEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { checkLogin() }
            override fun afterTextChanged(p0: Editable?) { }
        })
        binding.loginDetailPwdEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { checkLogin() }
            override fun afterTextChanged(p0: Editable?) { }
        })
    }

    private fun checkLogin() {
        if (binding.loginDetailEmailEt.text.isNotEmpty() && binding.loginDetailPwdEt.text.isNotEmpty()) {
            binding.loginDetailLoginBtn.setBackgroundResource(R.drawable.login_round_5_o)
            binding.loginDetailLoginBtn.setTextColor(ContextCompat.getColor(this@LoginDetailActivity, R.color.gray_50))
            binding.loginDetailLoginBtn.isEnabled = true
        } else {
            binding.loginDetailLoginBtn.setBackgroundResource(R.drawable.login_round_5_x)
            binding.loginDetailLoginBtn.setTextColor(ContextCompat.getColor(this@LoginDetailActivity, R.color.gray_700))
            binding.loginDetailLoginBtn.isEnabled = false
        }
    }
}