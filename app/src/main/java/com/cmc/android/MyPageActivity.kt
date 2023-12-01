package com.cmc.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivityMyPageBinding
import com.cmc.android.utils.getNickname
import com.cmc.android.utils.removeAccessToken
import com.cmc.android.utils.removeEmail
import com.cmc.android.utils.removeNickname
import com.cmc.android.utils.removePassword
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
            var intent = Intent(this, MyPageInfoActivity::class.java)
            startActivity(intent)
        }

        binding.myPageRuleCl.setOnClickListener {
            // UPDATE: 노션 연결
            var url = "https://makeus-challenge.notion.site/43da7aedefaf4eb5a227dd9a1c66be5b?pvs=4"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.myPagePersonalCl.setOnClickListener {
            var url = "https://makeus-challenge.notion.site/be7d5901cf834befafe088e03e362c96"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.myPageChangePwdCl.setOnClickListener {
            var intent = Intent(this, ChangePwdFirstActivity::class.java)
            startActivity(intent)
        }

        binding.myPageLogoutCl.setOnClickListener {
            var bottomSheetLogout = BottomSheetLogout()
            bottomSheetLogout.show(supportFragmentManager, "BottomSheetLogout")
            bottomSheetLogout.setOnDialogFinishListener(object: BottomSheetLogout.OnDialogFinishListener {
                override fun finish(result: Boolean) {
                    if (result) {
                        removeAccessToken()
                        removeRefreshToken()
                        removeEmail()
                        removePassword()
                        removeNickname()


                        var intent = Intent(this@MyPageActivity, LoginActivity::class.java)
                        startActivity(intent)
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