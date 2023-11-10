package com.cmc.android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android.databinding.ActivitySignupThirdBinding

class SignupThirdActivity: AppCompatActivity() {

    lateinit var binding: ActivitySignupThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupThirdBinding.inflate(layoutInflater)

        initClickListener()
        initFocusListener()
        initChangeListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.signupThirdOrderNumCl.setOnClickListener {
            var bottomSheetOrderNum = BottomSheetOrderNum()
            bottomSheetOrderNum.show(supportFragmentManager, "BottomSheetOrderNum")
            bottomSheetOrderNum.setOnDialogFinishListener(object: BottomSheetOrderNum.OnDialogFinishListener {
                override fun finish(orderNum: String) {
                    if (orderNum != "") {
                        binding.signupThirdOrderNumTv.text = orderNum

                    }

                    checkNext()
                }
            })
        }

        binding.signupThirdPositionCl.setOnClickListener {
            var bottomSheetPosition = BottomSheetPosition()
            bottomSheetPosition.show(supportFragmentManager, "BottomSheetPosition")
            bottomSheetPosition.setOnDialogFinishListener(object: BottomSheetPosition.OnDialogFinishListener {
                override fun finish(position: String) {
                    if (position != "") {
                        binding.signupThirdPositionTv.text = position
                    }

                    checkNext()
                }
            })
        }

        binding.signupThirdSignupBtn.setOnClickListener {
            startActivity(Intent(this, SignupCompleteActivity::class.java))
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
            && binding.signupThirdOrderNumTv.text != "기수를 선택해주세요"
            && binding.signupThirdPositionTv.text != "포지션을 선택해주세요") {
            binding.signupThirdSignupBtn.setBackgroundResource(R.drawable.login_round_5_o)
            binding.signupThirdSignupBtn.setTextColor(ContextCompat.getColor(this@SignupThirdActivity, R.color.gray_50))
            binding.signupThirdSignupBtn.isEnabled = true
        } else {
            binding.signupThirdSignupBtn.setBackgroundResource(R.drawable.login_round_5_x)
            binding.signupThirdSignupBtn.setTextColor(ContextCompat.getColor(this@SignupThirdActivity, R.color.gray_700))
            binding.signupThirdSignupBtn.isEnabled = false
        }
    }
}