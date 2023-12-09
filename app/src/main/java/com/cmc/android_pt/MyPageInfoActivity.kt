package com.cmc.android_pt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android_pt.databinding.ActivityMyPageInfoBinding
import com.cmc.android_pt.domain.req.UserInfoResponse
import com.cmc.android_pt.network.user.UserService
import com.cmc.android_pt.network.user.UserView
import com.cmc.android_pt.utils.partConvertFromServer

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