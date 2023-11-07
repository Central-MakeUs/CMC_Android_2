package com.cmc.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivitySignupSecondBinding

class SignupSecondActivity: AppCompatActivity() {

    lateinit var binding: ActivitySignupSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupSecondBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}