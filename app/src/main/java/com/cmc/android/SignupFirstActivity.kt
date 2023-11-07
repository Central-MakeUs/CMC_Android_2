package com.cmc.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android.databinding.ActivitySignupFirstBinding

class SignupFirstActivity: AppCompatActivity() {

    lateinit var binding: ActivitySignupFirstBinding
    private var radioStatus = arrayListOf(false, false, false, false, false)
    private lateinit var imageArray: ArrayList<ImageView>
    private var checkNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupFirstBinding.inflate(layoutInflater)

        imageArray = arrayListOf(
            binding.signupFirstAllIv, binding.signupFirstServiceIv, binding.signupFirstPersonalIv,
            binding.signupFirstLocationIv, binding.signupFirstMarketingIv
        )

        initClickListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.signupFirstAllIv.setOnClickListener {
            changeAllRadio()
        }

        binding.signupFirstServiceIv.setOnClickListener {
            radioCheckListener(1)
        }

        binding.signupFirstPersonalIv.setOnClickListener {
            radioCheckListener(2)
        }

        binding.signupFirstLocationIv.setOnClickListener {
            radioCheckListener(3)
        }

        binding.signupFirstMarketingIv.setOnClickListener {
            radioCheckListener(4)
        }

        binding.signupFirstNextBtn.setOnClickListener {
            startActivity(Intent(this, SignupSecondActivity::class.java))
        }
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
    }

    private fun changeAllRadio() {
        radioStatus[0] = !radioStatus[0]

        for (i in 0 until 5) {
            radioStatus[i] = radioStatus[0]

            if (radioStatus[0]) imageArray[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_radio_on))
            else imageArray[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_radio_off))
        }
    }
}