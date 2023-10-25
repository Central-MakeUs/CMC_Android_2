package com.cmc.android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        splashAnimation()

        setContentView(binding.root)
    }

    private fun splashAnimation() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            changeActivity(MainActivity::class.java)
            finish()
        }, 1500)
    }

    private fun changeActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }
}