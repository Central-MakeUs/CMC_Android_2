package com.cmc.android_pt

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android_pt.databinding.ActivityWebViewBinding

class WebViewActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)

        url = intent.getStringExtra("url").toString()

        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(url)

        setContentView(binding.root)
    }
}