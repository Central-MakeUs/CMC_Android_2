package com.cmc.android

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android.databinding.ActivityMainBinding
import com.cmc.android.domain.req.UserInfoResponse
import com.cmc.android.network.attendances.AttendanceSendView
import com.cmc.android.network.attendances.AttendanceService
import com.cmc.android.network.user.UserService
import com.cmc.android.network.user.UserView
import com.cmc.android.utils.partConvertFromServer
import com.cmc.android.utils.saveNickname
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity(), UserView, AttendanceSendView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var nickname: String
    private var generation: Int = 0
    private lateinit var part: String
    private lateinit var attendanceService: AttendanceService
    private lateinit var userService: UserService
    private lateinit var attendanceCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        initService()
        initClickListener()

        setContentView(binding.root)
    }

    private fun initService() {
        attendanceService = AttendanceService()
        attendanceService.setAttendanceSendView(this)
        userService = UserService()
        userService.setUserView(this)
        userService.getUserInfo()
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

        binding.mainAttendCheckCl.setOnClickListener {
            startActivity(Intent(this, AttendanceCheckActivity::class.java))
        }

        binding.mainSettingIv.setOnClickListener {
            startActivity(Intent(this, MyPageActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK) {
            val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
            attendanceCode = scanResult.contents
            attendanceService.sendAttendance(attendanceCode)
            Toast.makeText(this, scanResult.contents, Toast.LENGTH_LONG).show()
        }
    }

    override fun attendanceSendSuccessView() {
        var intent = Intent(this@MainActivity, ResultActivity::class.java)
        intent.putExtra("title", "00님의 \n출석이 완료되었어요!")
        intent.putExtra("content", "잠시 후 시작되는 세션에 집중해주세요 :)")
        intent.putExtra("btnText", "완료")
        startActivity(intent)
    }

    override fun attendanceSendFailureView() {
        Toast.makeText(this@MainActivity, "연결 성공", Toast.LENGTH_LONG).show()
    }

    override fun getUserInfoSuccessView(result: UserInfoResponse) {
        // UPDATE: 앞 글자 맞춰서 "은/는" 코드 추가하기!
        nickname = result.nickname
        saveNickname(nickname)
        generation = result.generation
        part = partConvertFromServer(result.part)

        val textData = "${nickname}은\nCMC ${generation}기 ${part}로\n참여중이에요"
        val builder = SpannableStringBuilder(textData)
        val colorBlueSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.main))
        builder.setSpan(colorBlueSpan, textData.length - 8 - part.length, textData.length - 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.mainTitle.text = builder
    }

    override fun getUserInfoFailureView() {

    }
}