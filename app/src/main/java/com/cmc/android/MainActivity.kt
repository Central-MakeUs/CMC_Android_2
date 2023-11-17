package com.cmc.android

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cmc.android.databinding.ActivityMainBinding
import com.cmc.android.databinding.ActivitySplashBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var nickname: String
    private var generation: Int = 0
    private lateinit var part: String

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        // result : 스캔된 결과

        // 내용이 없다면
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        }
        else { // 내용이 있다면

            // 1. Toast 메시지 출력.
            Toast.makeText(
                this,
                "Scanned: " + result.contents,
                Toast.LENGTH_LONG
            ).show()

            // 3. 웹 사이트 주소이면 ? 웹 뷰로 연결하자
            if ( result.contents.toString().startsWith("http") ) {
                val intent = Intent(this, QRActivity::class.java)
                intent.putExtra("url", result.contents.toString())
                startActivity(intent)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        nickname = "루나"
        generation = 12
        part = "Android"

        binding.mainTitle.text = "${nickname}는\nCMC ${generation}기 ${part}로\n참여중이에요"

        val textData = "${nickname}은\nCMC ${generation}기 ${part}로\n참여중이에요"
        val builder = SpannableStringBuilder(textData)
        val colorBlueSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.main))
        builder.setSpan(colorBlueSpan, textData.length - 8 - part.length, textData.length - 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.mainTitle.text = builder

        initClickListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.mainAttendCl.setOnClickListener {
            // startActivity(Intent(this, QRActivity::class.java))
            barcodeLauncher.launch(ScanOptions())
        }
    }
}