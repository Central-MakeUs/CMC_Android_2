package com.cmc.android.utils

import android.util.Log
import com.cmc.android.utils.ApplicationClass.Companion.mSharedPreferences

// AccessToken
fun removeAccessToken() {
    val editor = mSharedPreferences.edit()
    editor.remove("accessToken")
    editor.commit()
}

fun saveAccessToken(token: String?) {
    val editor = mSharedPreferences.edit()
    Log.d("TOKEN-CHECK", "accessToken = $token")
    editor.putString("accessToken", token)
    editor.apply()
}

// RefreshToken
fun removeRefreshToken() {
    val editor = mSharedPreferences.edit()
    editor.remove("refreshToken")
    editor.commit()
}

fun saveRefreshToken(token: String?) {
    val editor = mSharedPreferences.edit()
    editor.putString("refreshToken", token)
    editor.apply()
}

fun getAccessToken(): String? = mSharedPreferences.getString("accessToken", null)
fun getRefreshToken(): String? = mSharedPreferences.getString("refreshToken", null)