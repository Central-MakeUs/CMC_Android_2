package com.cmc.android

import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivityQrBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class QRActivity: AppCompatActivity() {

    lateinit var binding: ActivityQrBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrBinding.inflate(layoutInflater)

//        runQRcodeReader()

        setContentView(binding.root)

        // 웹 사이트 URL 주소 가져오기
        var getUrl = intent.getStringExtra("url").toString()

        // 웹 뷰 설정
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(getUrl)
    }

//    fun runQRcodeReader() {
//        val options = ScanOptions()
//        options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
//        options.setPrompt("Scan a barcode")
//        options.setCameraId(0)
//        options.setBeepEnabled(false)
//        options.setBarcodeImageEnabled(true)
//        options.setOrientationLocked(false)
//        barcodeLauncher.launch(options)
//    }
//
//    private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
//        ScanContract()
//    ) { result: ScanIntentResult ->
//        if (result.contents == null) {
//            Toast.makeText(this@QRActivity, "Cancelled", Toast.LENGTH_LONG).show()
//            Log.d("API-ERROR", "Cancelled")
//        } else {
//            Toast.makeText(
//                this@QRActivity,
//                "Scanned: " + result.contents,
//                Toast.LENGTH_LONG
//            ).show()
//            Log.d("API-ERROR", "${result.contents.toString()}")
//        }
//    }
}