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
        binding.signupThirdGenerationCl.setOnClickListener {
            var bottomSheetGeneration = BottomSheetGeneration()
            bottomSheetGeneration.show(supportFragmentManager, "BottomSheetGeneration")
            bottomSheetGeneration.setOnDialogFinishListener(object: BottomSheetGeneration.OnDialogFinishListener {
                override fun finish(generation: String) {
                    if (generation != "") {
                        binding.signupThirdGenerationTv.text = generation

                    }

                    checkNext()
                }
            })
        }

        binding.signupThirdPartCl.setOnClickListener {
            var bottomSheetPart = BottomSheetPart()
            bottomSheetPart.show(supportFragmentManager, "BottomSheetPart")
            bottomSheetPart.setOnDialogFinishListener(object: BottomSheetPart.OnDialogFinishListener {
                override fun finish(part: String) {
                    if (part != "") {
                        binding.signupThirdPartTv.text = part
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
}