package com.cmc.android.utils

fun partConvert(part: String): String {
    return when (part) {
        "Server", "server" -> "BACK_END"
        "Web", "web" -> "WEB"
        "iOS", "ios" -> "IOS"
        "Android", "android" -> "AOS"
        "Plan", "plan" -> "PLANNER"
        else -> "DESIGNER"
    }
}