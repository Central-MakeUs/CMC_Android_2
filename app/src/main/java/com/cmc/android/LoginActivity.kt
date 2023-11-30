package com.cmc.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        initClickListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.loginBtn.setOnClickListener {
            var intent = Intent(this, LoginDetailActivity::class.java)
            startActivity(intent)
        }

        binding.signupBtn.setOnClickListener {
            var intent = Intent(this, SignupFirstActivity::class.java)
            startActivity(intent)
        }
    }
}