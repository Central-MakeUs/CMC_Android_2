package com.cmc.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivityMyPageBinding
import com.cmc.android.utils.getNickname
import com.cmc.android.utils.removeAccessToken
import com.cmc.android.utils.removeNickname
import com.cmc.android.utils.removeRefreshToken

class MyPageActivity: AppCompatActivity() {

    lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageBinding.inflate(layoutInflater)

        initClickListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.myPageBackIv.setOnClickListener {
            finish()
        }

        binding.myPageMyInfoCl.setOnClickListener {
            startActivity(Intent(this, MyPageInfoActivity::class.java))
        }

        binding.myPageRuleCl.setOnClickListener {
            // UPDATE: 노션 연결
            var intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", "https://www.naver.com")
            startActivity(intent)
        }

        binding.myPagePersonalCl.setOnClickListener { 
            // UPDATE: 노션 연결
            var intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", "https://www.naver.com")
            startActivity(intent)
        }

        binding.myPageChangePwdCl.setOnClickListener {
            startActivity(Intent(this, ChangePwdFirstActivity::class.java))
        }

        binding.myPageLogoutCl.setOnClickListener {
            var bottomSheetLogout = BottomSheetLogout()
            bottomSheetLogout.show(supportFragmentManager, "BottomSheetLogout")
            bottomSheetLogout.setOnDialogFinishListener(object: BottomSheetLogout.OnDialogFinishListener {
                override fun finish(result: Boolean) {
                    if (result) {
                        removeAccessToken()
                        removeRefreshToken()
                        removeNickname()

                        startActivity(Intent(this@MyPageActivity, LoginActivity::class.java))
                        finishAffinity()
                    }
                }
            })
        }

        binding.myPageLeaveUserTv.setOnClickListener {
            var bottomSheetLeaveUser = BottomSheetLeaveUser()
            bottomSheetLeaveUser.show(supportFragmentManager, "BottomSheetLeaveUser")
            bottomSheetLeaveUser.setOnDialogFinishListener(object: BottomSheetLeaveUser.OnDialogFinishListener {
                override fun finish(result: Boolean) {
                    if (result) {
                        var intent = Intent(this@MyPageActivity, ResultActivity::class.java)
                        intent.putExtra("title", "${getNickname()}님\n언젠가 또 만나요!")
                        intent.putExtra("content", "현 기수 CMC활동중이라면, 불이익이 발생할 수 있습니다.")
                        intent.putExtra("btnText", "완료")
                    }
                }
            })
        }
    }
}