package com.cmc.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.cmc.android.databinding.ActivityMainBinding
import com.cmc.android.domain.notification.NotificationResult
import com.cmc.android.domain.req.UserInfoResponse
import com.cmc.android.network.attendances.AttendanceSendView
import com.cmc.android.network.attendances.AttendanceService
import com.cmc.android.network.notification.NotificationService
import com.cmc.android.network.notification.NotificationView
import com.cmc.android.network.user.UserService
import com.cmc.android.network.user.UserView
import com.cmc.android.utils.getNickname
import com.cmc.android.utils.partConvertFromServer
import com.cmc.android.utils.saveNickname
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity(), UserView, AttendanceSendView, NotificationView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var nickname: String
    private var generation: Int = 0
    private lateinit var part: String
    private lateinit var attendanceService: AttendanceService
    private lateinit var userService: UserService
    private lateinit var notificationService: NotificationService
    private lateinit var attendanceCode: String
    private var informList = arrayListOf<NotificationResult>()
    private lateinit var vpAdapter: InformRVAdapter
    private var currentPage = 0
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        initService()
        initViewPager()
        initClickListener()

        handler = Handler(Looper.getMainLooper()) {
            setPage()
            true
        }

        Thread(PagerRunnable()).start()

        setContentView(binding.root)
    }

    private fun initService() {
        attendanceService = AttendanceService()
        attendanceService.setAttendanceSendView(this)
        userService = UserService()
        userService.setUserView(this)
        userService.getUserInfo()
        notificationService = NotificationService()
        notificationService.setNotificationView(this)
        notificationService.getNotifications()
    }

    private fun initViewPager() {
        vpAdapter = InformRVAdapter(informList)
        binding.mainInformVp.adapter = vpAdapter
        binding.mainInformVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.mainInformCi.setViewPager(binding.mainInformVp)
        vpAdapter.setOnItemClickListener(object: InformRVAdapter.OnItemClickListener {
            override fun onItemClick(data: NotificationResult, position: Int) {
                var intent = Intent(this@MainActivity, WebViewActivity::class.java)
                intent.putExtra("url", data.notionUrl)
                startActivity(intent)
            }
        })
    }

    private fun setPage() {
        if (currentPage == 3) {
            currentPage = 0
        }
        binding.mainInformVp.setCurrentItem(currentPage, true)
        currentPage += 1
    }

    inner class PagerRunnable:Runnable{
        override fun run() {
            while(true){
                try {
                    Thread.sleep(5000)
                    handler.sendEmptyMessage(0)
                } catch (e : InterruptedException){
                    Log.e("ViewPager", "e = $e")
                }
            }
        }
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
            var intent = Intent(this, AttendanceCheckActivity::class.java)
            startActivity(intent)
        }

        binding.mainSettingIv.setOnClickListener {
            var intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        binding.mainAllInformCl.setOnClickListener {
            var url = "https://makeus-challenge.notion.site/2591216dc54f4928a0ebfce2d6ec4cfe"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.mainKakaoTalkCl.setOnClickListener {
            var url = "https://pf.kakao.com/_xcwDJT/chat"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.mainCmcWebCl.setOnClickListener {
            var url = "https://cmc.makeus.in/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK) {
            val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
            attendanceCode = scanResult.contents
            attendanceService.sendAttendance(attendanceCode)
        }
    }

    override fun attendanceSendSuccessView() {
        var intent = Intent(this@MainActivity, ResultActivity::class.java)
        intent.putExtra("title", "${getNickname()}님의 \n출석이 완료되었어요!")
        intent.putExtra("content", "잠시 후 시작되는 세션에 집중해주세요 :)")
        intent.putExtra("btnText", "완료")
        startActivity(intent)
    }

    override fun attendanceSendFailureView() {
        Toast.makeText(this@MainActivity, "연결 성공", Toast.LENGTH_LONG).show()
    }

    override fun getUserInfoSuccessView(result: UserInfoResponse) {
        nickname = result.nickname
        saveNickname(nickname)
        generation = result.generation
        part = partConvertFromServer(result.part)

        var lastLetterCheck = checkLastLetter(nickname, "은", "는")

        val textData = "${nickname}${lastLetterCheck}\nCMC ${generation}기 ${part}로\n참여중이에요"
        val builder = SpannableStringBuilder(textData)
        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.main))
        builder.setSpan(colorSpan, textData.length - 8 - part.length, textData.length - 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.mainTitle.text = builder
    }

    override fun getUserInfoFailureView() {

    }

    private fun checkLastLetter(name: String, firstValue: String, secondValue: String?): String {
        val lastName = name[name.length - 1]

        if (lastName.code < 0xAC00 || lastName.code > 0xD7A3) {
            return name
        }

        val selectedValue = if ((lastName.code - 0xAC00) % 28 > 0) firstValue else secondValue!!
        return name + selectedValue
    }

    override fun getNotificationSuccessView(result: ArrayList<NotificationResult>) {
        vpAdapter.addAllItems(result)
    }

    override fun getNotificationFailureView() {

    }
}