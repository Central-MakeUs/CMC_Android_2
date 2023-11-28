package com.cmc.android

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cmc.android.databinding.ActivityMyPageInfoBinding
import com.cmc.android.domain.req.UserInfoResponse
import com.cmc.android.network.user.UserService
import com.cmc.android.network.user.UserView
import com.cmc.android.utils.partConvertFromServer
import com.cmc.android.utils.saveNickname

class MyPageInfoActivity: AppCompatActivity(), UserView {

    lateinit var binding: ActivityMyPageInfoBinding
    private lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageInfoBinding.inflate(layoutInflater)

        initService()
        initClickListener()

        setContentView(binding.root)
    }

    private fun initService() {
        userService = UserService()
        userService.setUserView(this)
        userService.getUserInfo()
    }

    private fun initClickListener() {
        binding.myInfoBackIv.setOnClickListener {
            finish()
        }
    }

    override fun getUserInfoSuccessView(result: UserInfoResponse) {
        // UPDATE: 앞 글자 맞춰서 "은/는" 코드 추가하기!
        var nickname = result.nickname
        var generation = result.generation
        var part = partConvertFromServer(result.part)
        var email = result.email
        var name = result.name

        binding.myInfoNicknameValueTv.text = nickname
        binding.myInfoGenerationTv.text = "${generation}기"
        binding.myInfoPartTv.text = part
        binding.myInfoEmailValueTv.text = email
        binding.myInfoNameValueTv.text = name
    }

    override fun getUserInfoFailureView() {

    }
}