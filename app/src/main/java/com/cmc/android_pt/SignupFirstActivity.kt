package com.cmc.android_pt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android_pt.R
import com.cmc.android_pt.databinding.ActivitySignupFirstBinding

class SignupFirstActivity: AppCompatActivity() {

    lateinit var binding: ActivitySignupFirstBinding
    private var radioStatus = arrayListOf(false, false, false, false, false)
    private lateinit var imageArray: ArrayList<ImageView>
    private lateinit var arrowImages: ArrayList<ImageView>
    private var checkNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupFirstBinding.inflate(layoutInflater)

        imageArray = arrayListOf(
            binding.signupFirstAllIv, binding.signupFirstServiceIv, binding.signupFirstPersonalIv,
            binding.signupFirstLocationIv, binding.signupFirstMarketingIv
        )
        arrowImages = arrayListOf(
            binding.signupFirstServiceArrow, binding.signupFirstLocationArrow,
            binding.signupFirstMarketingArrow, binding.signupFirstPersonalArrow
        )

        initClickListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.signupFirstBackIv.setOnClickListener {
            finish()
        }

        binding.signupFirstAllIv.setOnClickListener { changeAllRadio() }
        binding.signupFirstServiceIv.setOnClickListener { radioCheckListener(1) }
        binding.signupFirstPersonalIv.setOnClickListener { radioCheckListener(2) }
        binding.signupFirstLocationIv.setOnClickListener { radioCheckListener(3) }
        binding.signupFirstMarketingIv.setOnClickListener { radioCheckListener(4) }

        binding.signupFirstServiceArrow.setOnClickListener { connectUrl("https://makeus-challenge.notion.site/ce8e31baeee9444382e69a87bae418f2") }
        binding.signupFirstPersonalArrow.setOnClickListener { connectUrl("https://makeus-challenge.notion.site/be7d5901cf834befafe088e03e362c96") }
        binding.signupFirstLocationArrow.setOnClickListener { connectUrl("https://makeus-challenge.notion.site/28a562a7a37c4962b50f45a0ce59d9b2") }
        binding.signupFirstMarketingArrow.setOnClickListener { connectUrl("https://makeus-challenge.notion.site/1096cea2c98e4f07b8211f95cf4ab93c") }

        binding.signupFirstNextBtn.setOnClickListener {
            var intent = Intent(this, SignupSecondActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }
    }

    private fun connectUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

    private fun radioCheckListener(index: Int) {
        radioStatus[index] = !radioStatus[index]

        if (radioStatus[index]) {
            imageArray[index].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_radio_on))

            if (radioStatus[1] && radioStatus[2] && radioStatus[3] && radioStatus[4]) {
                radioStatus[0] = true
                imageArray[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_radio_on))
            }
        } else {
            radioStatus[0] = false
            
            imageArray[index].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_radio_off))
            imageArray[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_radio_off))
        }

        checkNext()
    }

    private fun changeAllRadio() {
        radioStatus[0] = !radioStatus[0]

        for (i in 0 until 5) {
            radioStatus[i] = radioStatus[0]

            if (radioStatus[0]) imageArray[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_radio_on))
            else imageArray[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_radio_off))
        }

        checkNext()
    }

    private fun checkNext() {
        if (radioStatus[1] && radioStatus[2]) {
            binding.signupFirstNextBtn.setBackgroundResource(R.drawable.login_round_5_o)
            binding.signupFirstNextBtn.setTextColor(ContextCompat.getColor(this@SignupFirstActivity, R.color.gray_50))
            binding.signupFirstNextBtn.isEnabled = true
        } else {
            binding.signupFirstNextBtn.setBackgroundResource(R.drawable.login_round_5_x)
            binding.signupFirstNextBtn.setTextColor(ContextCompat.getColor(this@SignupFirstActivity, R.color.gray_700))
            binding.signupFirstNextBtn.isEnabled = false
        }
    }
}