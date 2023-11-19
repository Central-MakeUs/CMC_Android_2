package com.cmc.android

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android.databinding.ActivityMainBinding
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var nickname: String
    private var generation: Int = 0
    private lateinit var part: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // UPDATE: 파트 글자 보라색 설정 코드
        nickname = "루나"
        generation = 12
        part = "Android"

        val textData = "${nickname}은\nCMC ${generation}기 ${part}로\n참여중이에요"
        val builder = SpannableStringBuilder(textData)
        val colorBlueSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.main))
        builder.setSpan(colorBlueSpan, textData.length - 8 - part.length, textData.length - 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.mainTitle.text = builder

        initClickListener()

        setContentView(binding.root)
    }

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        if (result.contents == null) {
            val originalIntent = result.originalIntent
            if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                Toast.makeText(this@MainActivity, "카메라를 인식할 수 없습니다.", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this@MainActivity, result.contents, Toast.LENGTH_LONG).show()
        }
    }

    private fun initClickListener() {
        // UPDATE: Deprecated 함수들 수정
        binding.mainAttendCl.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.captureActivity = QRActivity::class.java
            integrator.initiateScan()

            val options = ScanOptions().setOrientationLocked(false).setCaptureActivity(
                QRActivity::class.java
            )

            options.setBarcodeImageEnabled(true)
            options.setBeepEnabled(false)
            barcodeLauncher.launch(options)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK) {
            val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
            Toast.makeText(this, scanResult.contents, Toast.LENGTH_LONG).show()
        }
    }
}