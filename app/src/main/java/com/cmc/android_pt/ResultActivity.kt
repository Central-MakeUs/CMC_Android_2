package com.cmc.android_pt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android_pt.databinding.ActivityResultBinding

class ResultActivity: AppCompatActivity() {

    lateinit var binding: ActivityResultBinding
    private var title = ""
    private var content = ""
    private var btnText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)

        title = intent.getStringExtra("title").toString()
        content = intent.getStringExtra("content").toString()
        btnText = intent.getStringExtra("btnText").toString()

        binding.activityResultTitleTv.text = title
        binding.activityResultContentTv.text = content
        binding.activityResultBtn.text = btnText

        initClickListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.activityResultBtn.setOnClickListener {
            var intent = Intent(this, LoginDetailActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}