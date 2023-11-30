package com.cmc.android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.cmc.android.databinding.ActivitySplashBinding
import com.cmc.android.domain.auth.AuthResult
import com.cmc.android.domain.auth.req.LoginRequest
import com.cmc.android.network.auth.AuthService
import com.cmc.android.network.auth.LoginView
import com.cmc.android.utils.getEmail
import com.cmc.android.utils.getPassword
import com.cmc.android.utils.saveAccessToken
import com.cmc.android.utils.saveEmail
import com.cmc.android.utils.savePassword
import com.cmc.android.utils.saveRefreshToken

class SplashActivity : AppCompatActivity(), LoginView {

    private lateinit var binding: ActivitySplashBinding
    private var autoLogin: Boolean = false
    private lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        initService()
        checkAutoLogin()
        splashAnimation()

        setContentView(binding.root)
    }

    private fun initService() {
        authService = AuthService()
        authService.setLoginView(this)
    }

    private fun checkAutoLogin() {
        autoLogin = getEmail() != null && getPassword() != null
    }

    private fun splashAnimation() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (autoLogin) {
                var request = LoginRequest(getEmail().toString(), getPassword().toString())
                authService.login(request)
            } else changeActivity(LoginActivity::class.java)
        }, 1500)
    }

    override fun loginSuccessView(result: AuthResult) {
        saveAccessToken(result.accessToken)
        saveRefreshToken(result.refreshToken)

        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun loginFailureView() {
        changeActivity(LoginActivity::class.java)
        finish()
    }

    private fun changeActivity(activity: Class<*>) {
        var intent = Intent(this, activity)
        startActivity(intent)
        finish()
    }
}