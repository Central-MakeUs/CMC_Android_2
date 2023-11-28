package com.cmc.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivityMyPageInfoBinding

class MyPageInfoActivity: AppCompatActivity() {

    lateinit var binding: ActivityMyPageInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageInfoBinding.inflate(layoutInflater)

        initClickListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.myInfoBackIv.setOnClickListener {
            finish()
        }
    }
}