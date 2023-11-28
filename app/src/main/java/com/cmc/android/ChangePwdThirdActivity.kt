package com.cmc.android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android.databinding.ActivityChangePwdThirdBinding
import com.cmc.android.network.auth.AuthInterface
import com.cmc.android.network.auth.AuthService
import com.cmc.android.network.auth.ChangePasswordView

class ChangePwdThirdActivity: AppCompatActivity(), ChangePasswordView {

    private lateinit var binding: ActivityChangePwdThirdBinding
    private var firstPwdMode = false
    private var secondPwdMode = false
    private var email = ""
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePwdThirdBinding.inflate(layoutInflater)

        email = intent.getStringExtra("email").toString()

        initService()
        initClickListener()
        initFocusListener()
        initChangeListener()

        setContentView(binding.root)
    }

    private fun initService() {
        authService = AuthService()
        authService.setChangePasswordView(this)
    }

    private fun initClickListener() {
        binding.changePwdBackIv.setOnClickListener {
            finish()
        }

        binding.changePwdModeIv1.setOnClickListener {
            firstPwdMode = !firstPwdMode

            var pwdModeIc = if (firstPwdMode) R.drawable.ic_show else R.drawable.ic_hide
            var transformationMethod = if (firstPwdMode) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()

            binding.changePwdModeIv1.setImageResource(pwdModeIc)
            binding.changePwdEt1.transformationMethod = transformationMethod
            binding.changePwdEt1.setSelection(binding.changePwdEt1.text.length)
        }

        binding.changePwdModeIv2.setOnClickListener {
            secondPwdMode = !secondPwdMode

            var pwdModeIc = if (secondPwdMode) R.drawable.ic_show else R.drawable.ic_hide
            var transformationMethod = if (secondPwdMode) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()

            binding.changePwdModeIv2.setImageResource(pwdModeIc)
            binding.changePwdEt2.transformationMethod = transformationMethod
            binding.changePwdEt2.setSelection(binding.changePwdEt2.text.length)
        }

        binding.changePwdNextBtn.setOnClickListener {
            authService.changePassword(email, binding.changePwdEt1.text.toString())
        }
    }

    private fun initFocusListener() {
        binding.changePwdEt1.setOnFocusChangeListener { _, focus ->
            if (focus) {
                binding.changePwdLine1.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_200))
            } else {
                binding.changePwdLine1.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_700))
            }
        }

        binding.changePwdEt2.setOnFocusChangeListener { _, focus ->
            if (focus) {
                binding.changePwdLine2.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_200))
            } else {
                binding.changePwdLine2.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_700))
            }
        }
    }

    private fun initChangeListener() {
        binding.changePwdEt1.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { checkNext() }
            override fun afterTextChanged(p0: Editable?) { }
        })
        binding.changePwdEt2.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { checkNext() }
            override fun afterTextChanged(p0: Editable?) { }
        })
    }

    private fun checkNext() {
        var str = binding.changePwdEt1.text.toString()

        var checkEng = str.matches(Regex("(?=.*[a-zA-Z]).*"))
        changeCheck(checkEng, binding.changePwdEngIv, binding.changePwdEngTv)

        var checkNum = str.matches(Regex("(?=.*[0-9]).*"))
        changeCheck(checkNum, binding.changePwdNumIv, binding.changePwdNumTv)

        var checkLength = str.length in 8..16
        changeCheck(checkLength, binding.changePwdLengthIv, binding.changePwdLengthTv)

        var checkSame = binding.changePwdEt1.text.toString() == binding.changePwdEt2.text.toString()
        changeCheck(checkSame, binding.changePwdCheckIv, binding.changePwdCheckTv)

        if (checkEng && checkNum && checkLength && checkSame) {
            binding.changePwdNextBtn.setBackgroundResource(R.drawable.login_round_5_o)
            binding.changePwdNextBtn.setTextColor(ContextCompat.getColor(this@ChangePwdThirdActivity, R.color.gray_50))
            binding.changePwdNextBtn.isEnabled = true
        } else {
            binding.changePwdNextBtn.setBackgroundResource(R.drawable.login_round_5_x)
            binding.changePwdNextBtn.setTextColor(ContextCompat.getColor(this@ChangePwdThirdActivity, R.color.gray_700))
            binding.changePwdNextBtn.isEnabled = false
        }
    }

    private fun changeCheck(result: Boolean, imageView: ImageView, textView: TextView) {
        if (result) {
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_check_o))
            textView.setTextColor(ContextCompat.getColor(this, R.color.main))
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_check_x))
            textView.setTextColor(ContextCompat.getColor(this, R.color.gray_700))
        }
    }

    override fun changePasswordSuccessView() {
        var bottomSheetTopBottomTitle = BottomSheetTopBottomTitle()
        var bundle = Bundle()
        bundle.putString("topTitle", "비밀번호가 변경되었어요")
        bundle.putString("bottomTitle", "다시 로그인해주세요 :)")
        bottomSheetTopBottomTitle.arguments = bundle
        bottomSheetTopBottomTitle.show(supportFragmentManager, "BottomSheetChangePwd")
        bottomSheetTopBottomTitle.setOnDialogFinishListener(object: BottomSheetTopBottomTitle.OnDialogFinishListener {
            override fun finish(result: Boolean?) {
                if (result == true) {
                    startActivity(Intent(this@ChangePwdThirdActivity, LoginActivity::class.java))
                    finishAffinity()
                }
            }
        })
    }

    override fun changePasswordFailureView() {

    }
}