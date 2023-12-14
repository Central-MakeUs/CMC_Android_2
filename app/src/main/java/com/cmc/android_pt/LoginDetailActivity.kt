package com.cmc.android_pt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android_pt.R
import com.cmc.android_pt.domain.auth.AuthResult
import com.cmc.android_pt.domain.auth.req.LoginRequest
import com.cmc.android_pt.databinding.ActivityLoginDetailBinding
import com.cmc.android_pt.network.auth.AuthService
import com.cmc.android_pt.network.auth.LoginView
import com.cmc.android_pt.utils.saveAccessToken
import com.cmc.android_pt.utils.saveEmail
import com.cmc.android_pt.utils.savePassword
import com.cmc.android_pt.utils.saveRefreshToken
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class LoginDetailActivity: AppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginDetailBinding
    private lateinit var authService: AuthService
    private var pwdMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginDetailBinding.inflate(layoutInflater)

        initService()
        initClickListener()
        initFocusListener()
        initChangeListener()
        initKeyboardListener()
        setContentView(binding.root)
    }

    private fun initService() {
        authService = AuthService()
        authService.setLoginView(this)
    }

    private fun initClickListener() {
        binding.loginDetailBackIv.setOnClickListener {
            finish()
        }

        binding.loginDetailPwdModeIv.setOnClickListener {
            pwdMode = !pwdMode

            var pwdModeIc = if (pwdMode) R.drawable.ic_show else R.drawable.ic_hide
            var transformationMethod = if (pwdMode) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()

            binding.loginDetailPwdModeIv.setImageResource(pwdModeIc)
            binding.loginDetailPwdEt.transformationMethod = transformationMethod
            binding.loginDetailPwdEt.setSelection(binding.loginDetailPwdEt.text.length)
        }

        binding.loginDetailFindIdTv.setOnClickListener {
            var bottomSheetTopBottomTitle = BottomSheetTopBottomTitle()
            var bundle = Bundle()
            bundle.putString("topTitle", "아이디 찾기는")
            bundle.putString("bottomTitle", "운영진에게 문의해주세요 :)")
            bottomSheetTopBottomTitle.arguments = bundle
            bottomSheetTopBottomTitle.show(supportFragmentManager, "BottomSheetFindId")
        }

        binding.loginDetailFindPwdTv.setOnClickListener {
            var intent = Intent(this, ChangePwdFirstActivity::class.java)
            startActivity(intent)
        }

        binding.loginGoSignupTv.setOnClickListener {
            var intent = Intent(this, SignupFirstActivity::class.java)
            startActivity(intent)
        }

        binding.loginDetailLoginBtn.setOnClickListener {
            var request = LoginRequest(binding.loginDetailEmailEt.text.toString(), binding.loginDetailPwdEt.text.toString())
            authService.login(request)
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

    private fun initKeyboardListener() {
        setEventListener(
            this@LoginDetailActivity,
            KeyboardVisibilityEventListener { isOpen: Boolean ->
                if (isOpen) {
                    binding.loginGoSignupTv.visibility = View.INVISIBLE
                    binding.loginCheckAccountTv.visibility = View.INVISIBLE
                } else {
                    binding.loginGoSignupTv.visibility = View.VISIBLE
                    binding.loginCheckAccountTv.visibility = View.VISIBLE
                }
            }
        )
    }

    override fun loginSuccessView(result: AuthResult) {
        Log.d("API-TEST", "result = $result")
        saveEmail(binding.loginDetailEmailEt.text.toString())
        savePassword(binding.loginDetailPwdEt.text.toString())
        saveAccessToken(result.accessToken)
        saveRefreshToken(result.refreshToken)

        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    override fun loginFailureView() {
        var bottomSheetTitleContent = BottomSheetTitleContent()
        var bundle = Bundle()
        bundle.putString("title", "존재하지 않는 계정이에요")
        bundle.putString("content", "아이디 또는 비밀번호를 확인해주세요!")
        bottomSheetTitleContent.arguments = bundle
        bottomSheetTitleContent.show(supportFragmentManager, "BottomSheetLoginFail")
    }
}