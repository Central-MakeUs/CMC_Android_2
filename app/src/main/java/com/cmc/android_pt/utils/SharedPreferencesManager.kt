package com.cmc.android_pt.utils

import android.util.Log
import com.cmc.android_pt.utils.ApplicationClass.Companion.mSharedPreferences

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

// Email
fun removeEmail() {
    val editor = mSharedPreferences.edit()
    editor.remove("email")
    editor.commit()
}

fun saveEmail(email: String?) {
    val editor = mSharedPreferences.edit()
    editor.putString("email", email)
    editor.apply()
}

// Password
fun removePassword() {
    val editor = mSharedPreferences.edit()
    editor.remove("password")
    editor.commit()
}

fun savePassword(password: String?) {
    val editor = mSharedPreferences.edit()
    editor.putString("password", password)
    editor.apply()
}

// Nickname
fun removeNickname() {
    val editor = mSharedPreferences.edit()
    editor.remove("nickname")
    editor.commit()
}

fun saveNickname(nickname: String?) {
    val editor = mSharedPreferences.edit()
    editor.putString("nickname", nickname)
    editor.apply()
}

fun getAccessToken(): String? = mSharedPreferences.getString("accessToken", null)
fun getRefreshToken(): String? = mSharedPreferences.getString("refreshToken", null)
fun getNickname(): String? = mSharedPreferences.getString("nickname", null)
fun getEmail(): String? = mSharedPreferences.getString("email", null)
fun getPassword(): String? = mSharedPreferences.getString("password", null)