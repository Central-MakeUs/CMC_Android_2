package com.cmc.android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android.databinding.ActivityFindPwdBinding
import com.cmc.android.databinding.ActivityFindPwdNumberBinding
import java.text.DecimalFormat
import java.util.Timer
import kotlin.concurrent.timer

class FindPwdNumberActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFindPwdNumberBinding
    private var timer: Timer? = null
    private var remainTime = 180
    private var checkNumber = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPwdNumberBinding.inflate(layoutInflater)

        initClickListener()
        initFocusListener()
        initChangeListener()
        startTimer()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.findPwdNumberRequestBtn.setOnClickListener {
            var bottomSheetTitleContent = BottomSheetTitleContent()
            var bundle = Bundle()
            var bottomSheetTag = ""
            bundle.putString("title", "인증번호를 전송했어요")
            bundle.putString("content", "3분 내 인증번호를 입력해주세요 :)")
            bottomSheetTag = "BottomSheetNumberRequest"

            bottomSheetTitleContent.arguments = bundle
            bottomSheetTitleContent.show(supportFragmentManager, bottomSheetTag)
        }

        binding.findPwdChangeBtn.setOnClickListener {
            // UPDATE: API 연동 후 수정
            Log.d("TEST", "checkNumber = $checkNumber")
            if (checkNumber) {
                // MEMO: If 인증번호가 맞았다면
                var intent = Intent(this@FindPwdNumberActivity, ChangePwdActivity::class.java)
                startActivity(intent)
            } else {
                // MEMO: If 인증번호가 틀렸다면
                var bottomSheetTitleContent = BottomSheetTitleContent()
                var bundle = Bundle()
                var bottomSheetTag = ""
                bundle.putString("title", "올바르지 않은 인증번호에요")
                bundle.putString("content", "인증번호를 확인해주세요 :(")
                bottomSheetTag = "BottomSheetNumberFail"

                bottomSheetTitleContent.arguments = bundle
                bottomSheetTitleContent.show(supportFragmentManager, bottomSheetTag)
            }

            // UPDATE: 아래 부분 삭제하기!
            checkNumber = !checkNumber
        }

        binding.findPwdNumberRequestBtn.setOnClickListener {
            timer?.cancel()
            startTimer()
        }
    }

    private fun initFocusListener() {
        binding.findPwdNumberNumberEt.setOnFocusChangeListener { _, focus ->
            if (focus) {
                binding.findPwdNumberNumberLine.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_200))
            } else {
                binding.findPwdNumberNumberLine.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_700))
            }
        }
    }

    private fun initChangeListener() {
        binding.findPwdNumberNumberEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.findPwdNumberNumberEt.text.isNotEmpty()) {
                    binding.findPwdChangeBtn.setBackgroundResource(R.drawable.login_round_5_o)
                    binding.findPwdChangeBtn.setTextColor(ContextCompat.getColor(this@FindPwdNumberActivity, R.color.gray_50))
                    binding.findPwdChangeBtn.isEnabled = true
                } else {
                    binding.findPwdChangeBtn.setBackgroundResource(R.drawable.login_round_5_x)
                    binding.findPwdChangeBtn.setTextColor(ContextCompat.getColor(this@FindPwdNumberActivity, R.color.gray_700))
                    binding.findPwdChangeBtn.isEnabled = false
                }
            }
            override fun afterTextChanged(p0: Editable?) { }
        })
    }

    private fun startTimer() {
        remainTime = 180
        timer = timer(period = 1000) {
            runOnUiThread {
                var minute = (remainTime - 1) / 60
                var second = (remainTime - 1) % 60
                binding.findPwdNumberNumberTimeTv.text = "${DecimalFormat("00").format(minute)}:${DecimalFormat("00").format(second)}"
                remainTime -= 1

                if (remainTime == 0) timer?.cancel()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}