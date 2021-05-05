package com.mashup.ipdam.utils

import java.text.SimpleDateFormat

object DateUtil {
    fun getDateFormatter(): SimpleDateFormat {
        return SimpleDateFormat("yyyy.MM.dd")
    }
}