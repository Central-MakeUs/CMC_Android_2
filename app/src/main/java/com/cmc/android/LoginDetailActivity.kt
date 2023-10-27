package com.cmc.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivityLoginBinding
import com.cmc.android.databinding.ActivityLoginDetailBinding

class LoginDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}