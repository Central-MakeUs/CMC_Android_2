package com.cmc.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivityChangePwdBinding

class ChangePwdActivity: AppCompatActivity() {

    private lateinit var binding: ActivityChangePwdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePwdBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}