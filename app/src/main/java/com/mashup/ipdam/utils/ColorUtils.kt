package com.mashup.ipdam.utils

object ColorUtils {
    fun getHexColor(color: Int) =
        String.format("#%06X", (0xFFFFFF and color))
}