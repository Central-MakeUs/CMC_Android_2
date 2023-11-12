package com.cmc.android.utils

import android.util.Log
import com.cmc.android.utils.ApplicationClass.Companion.mSharedPreferences

fun saveJwt(jwt: String){
    Log.d("jwt", jwt)
    val editor = mSharedPreferences.edit()
    editor.putString("jwt", jwt)
    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString("jwt", null)