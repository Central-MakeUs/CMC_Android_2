package com.cmc.android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android.databinding.ActivitySignupSecondBinding
import com.cmc.android.network.AuthService
import com.cmc.android.network.EmailView

class SignupSecondActivity: AppCompatActivity(), EmailView {

    lateinit var binding: ActivitySignupSecondBinding
    private lateinit var emailService: AuthService
    private var firstPwdMode = false
    private var secondPwdMode = false
    private var validationCheck = arrayListOf(false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupSecondBinding.inflate(layoutInflater)

        initService()
        initClickListener()
        initFocusListener()
        initChangeListener()

        setContentView(binding.root)
    }

    private fun initService() {
        emailService = AuthService()
        emailService.setEmailView(this)
    }

    private fun initClickListener() {
        binding.signupSecondEmailCheckTv.setOnClickListener {
            emailService.checkEmail(binding.signupSecondEmailEt.text.toString())
        }

        binding.signupSecondPwdModeIv1.setOnClickListener {
            firstPwdMode = !firstPwdMode

            var pwdModeIc = if (firstPwdMode) R.drawable.ic_show else R.drawable.ic_hide
            var transformationMethod = if (firstPwdMode) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()

            binding.signupSecondPwdModeIv1.setImageResource(pwdModeIc)
            binding.signupSecondPwdEt1.transformationMethod = transformationMethod
            binding.signupSecondPwdEt1.setSelection(binding.signupSecondPwdEt1.text.length)
        }

        binding.signupSecondPwdModeIv2.setOnClickListener {
            secondPwdMode = !secondPwdMode

            var pwdModeIc = if (secondPwdMode) R.drawable.ic_show else R.drawable.ic_hide
            var transformationMethod = if (secondPwdMode) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()

            binding.signupSecondPwdModeIv2.setImageResource(pwdModeIc)
            binding.signupSecondPwdEt2.transformationMethod = transformationMethod
            binding.signupSecondPwdEt2.setSelection(binding.signupSecondPwdEt2.text.length)
        }

        binding.signupSecondNextBtn.setOnClickListener {
            var intent = Intent(this, SignupThirdActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("email", binding.signupSecondEmailEt.text.toString())
            intent.putExtra("password", binding.signupSecondPwdEt1.text.toString())
            intent.putExtra("name", binding.signupSecondNameEt.text.toString())
            startActivity(intent)
        }
    }

    private fun initFocusListener() {
        binding.signupSecondPwdEt1.setOnFocusChangeListener { _, focus ->
            if (focus) {
                binding.signupSecondPwdLine1.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_200))
            } else {
                binding.signupSecondPwdLine1.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_700))
            }
        }

        binding.signupSecondPwdEt2.setOnFocusChangeListener { _, focus ->
            if (focus) {
                binding.signupSecondPwdLine2.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_200))
            } else {
                binding.signupSecondPwdLine2.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_700))
            }
        }
    }

    private fun initChangeListener() {
        binding.signupSecondEmailEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { validationCheck[0] = false }
            override fun afterTextChanged(p0: Editable?) { }
        })
        binding.signupSecondPwdEt1.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { checkPwd() }
            override fun afterTextChanged(p0: Editable?) { }
        })
        binding.signupSecondPwdEt2.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { checkPwd() }
            override fun afterTextChanged(p0: Editable?) { }
        })
        binding.signupSecondNameEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { checkName() }
            override fun afterTextChanged(p0: Editable?) { }
        })
    }

    private fun checkPwd() {
        var str = binding.signupSecondPwdEt1.text.toString()

        var checkEng = str.matches(Regex("(?=.*[a-zA-Z]).*"))
        changeCheck(checkEng, binding.signupSecondEngIv, binding.signupSecondEngTv)

        var checkNum = str.matches(Regex("(?=.*[0-9]).*"))
        changeCheck(checkNum, binding.signupSecondNumIv, binding.signupSecondNumTv)

        var checkLength = str.length in 8..16
        changeCheck(checkLength, binding.signupSecondLengthIv, binding.signupSecondLengthTv)

        var checkSame = binding.signupSecondPwdEt1.text.toString() == binding.signupSecondPwdEt2.text.toString()
        changeCheck(checkSame, binding.signupSecondCheckIv, binding.signupSecondCheckTv)

        validationCheck[1] = checkEng && checkNum && checkLength && checkSame

        checkNext()
    }

    private fun checkName() {
        var checkName = binding.signupSecondNameEt.text.isNotEmpty()
        validationCheck[2] = checkName

        checkNext()
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

    private fun checkNext() {
        if (validationCheck[0] && validationCheck[1] && validationCheck[2]) {
            binding.signupSecondNextBtn.setBackgroundResource(R.drawable.login_round_5_o)
            binding.signupSecondNextBtn.setTextColor(ContextCompat.getColor(this@SignupSecondActivity, R.color.gray_50))
            binding.signupSecondNextBtn.isEnabled = true
        } else {
            binding.signupSecondNextBtn.setBackgroundResource(R.drawable.login_round_5_x)
            binding.signupSecondNextBtn.setTextColor(ContextCompat.getColor(this@SignupSecondActivity, R.color.gray_700))
            binding.signupSecondNextBtn.isEnabled = false
        }
    }

    override fun emailSuccessView(result: String) {
        validationCheck[0] = true
        checkNext()
    }

    override fun emailFailureView(message: String) {
        validationCheck[1] = false
    }
}