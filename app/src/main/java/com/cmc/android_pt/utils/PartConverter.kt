package com.cmc.android_pt.utils

fun partConvertToServer(part: String): String {
    return when (part) {
        "Server", "server" -> "BACK_END"
        "Web", "web" -> "WEB"
        "iOS", "ios" -> "IOS"
        "Android", "android" -> "AOS"
        "Plan", "plan" -> "PLANNER"
        else -> "DESIGNER"
    }
}

fun partConvertFromServer(part: String): String {
    return when (part) {
        "BACK_END" -> "Server"
        "WEB" -> "Web"
        "IOS" -> "iOS"
        "AOS" -> "Android"
        "PLANNER" -> "Plan"
        else -> "Design"
    }
}