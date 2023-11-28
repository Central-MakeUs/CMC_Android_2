package com.cmc.android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android.domain.auth.AuthResult
import com.cmc.android.domain.auth.req.SignupRequest
import com.cmc.android.databinding.ActivitySignupThirdBinding
import com.cmc.android.network.auth.AuthService
import com.cmc.android.network.auth.SignupView
import com.cmc.android.utils.partConvertToServer
import com.cmc.android.utils.saveAccessToken
import com.cmc.android.utils.saveRefreshToken

class SignupThirdActivity: AppCompatActivity(), SignupView {

    lateinit var binding: ActivitySignupThirdBinding
    lateinit var signupService: AuthService
    lateinit var email: String
    lateinit var password: String
    lateinit var name: String
    var generation: String = ""
    var part: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupThirdBinding.inflate(layoutInflater)

        email = intent.getStringExtra("email").toString()
        password = intent.getStringExtra("password").toString()
        name = intent.getStringExtra("name").toString()

        initService()
        initClickListener()
        initFocusListener()
        initChangeListener()

        setContentView(binding.root)
    }

    private fun initService() {
        signupService = AuthService()
        signupService.setSignupView(this)
    }

    private fun initClickListener() {
        binding.signupThirdBackIv.setOnClickListener {
            finish()
        }

        binding.signupThirdGenerationCl.setOnClickListener {
            var bottomSheetGeneration = BottomSheetGeneration()
            bottomSheetGeneration.show(supportFragmentManager, "BottomSheetGeneration")
            bottomSheetGeneration.setOnDialogFinishListener(object: BottomSheetGeneration.OnDialogFinishListener {
                override fun finish(generationData: String) {
                    if (generationData != "") {
                        binding.signupThirdGenerationTv.text = generationData
                        generation = generationData.substring(0, generationData.length - 1)
                    }

                    checkNext()
                }
            })
        }

        binding.signupThirdPartCl.setOnClickListener {
            var bottomSheetPart = BottomSheetPart()
            bottomSheetPart.show(supportFragmentManager, "BottomSheetPart")
            bottomSheetPart.setOnDialogFinishListener(object: BottomSheetPart.OnDialogFinishListener {
                override fun finish(partData: String) {
                    if (partData != "") {
                        binding.signupThirdPartTv.text = partData
                        part = partConvertToServer(partData)
                    }

                    checkNext()
                }
            })
        }

        binding.signupThirdSignupBtn.setOnClickListener {
            signupService.signup(SignupRequest(email, password, binding.signupThirdNicknameEt.text.toString(), name, generation.toInt(), part))
        }
    }

    private fun initFocusListener() {
        binding.signupThirdNicknameEt.setOnFocusChangeListener { _, focus ->
            if (focus) {
                binding.signupThirdNicknameLine.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_200))
            } else {
                binding.signupThirdNicknameLine.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_700))
            }
        }
    }

    private fun initChangeListener() {
        binding.signupThirdNicknameEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { checkNext() }
            override fun afterTextChanged(p0: Editable?) { }
        })
    }

    private fun checkNext() {
        if (binding.signupThirdNicknameEt.text.isNotEmpty()
            && binding.signupThirdGenerationTv.text != "기수를 선택해주세요"
            && binding.signupThirdPartTv.text != "포지션을 선택해주세요") {
            binding.signupThirdSignupBtn.setBackgroundResource(R.drawable.login_round_5_o)
            binding.signupThirdSignupBtn.setTextColor(ContextCompat.getColor(this@SignupThirdActivity, R.color.gray_50))
            binding.signupThirdSignupBtn.isEnabled = true
        } else {
            binding.signupThirdSignupBtn.setBackgroundResource(R.drawable.login_round_5_x)
            binding.signupThirdSignupBtn.setTextColor(ContextCompat.getColor(this@SignupThirdActivity, R.color.gray_700))
            binding.signupThirdSignupBtn.isEnabled = false
        }
    }

    override fun signupSuccessView(result: AuthResult) {
        Log.d("SIGNUP-SUCCESS", "result = $result")
        saveAccessToken(result.accessToken)
        saveRefreshToken(result.refreshToken)

        val intent = Intent(this@SignupThirdActivity, ResultActivity::class.java).apply {
            putExtra("title", "회원가입 신청이\n완료되었어요!")
            putExtra("content", "신청이 수락될 때까지 조금만 기다려주세요 :)")
            putExtra("btnText", "확인")
        }
        startActivity(intent)
        finishAffinity()
    }

    override fun signupFailureView(message: String) {

    }
}