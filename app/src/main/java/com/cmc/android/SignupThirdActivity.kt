package com.cmc.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivitySignupThirdBinding

class SignupThirdActivity: AppCompatActivity() {

    lateinit var binding: ActivitySignupThirdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupThirdBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}