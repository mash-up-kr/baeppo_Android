package com.mashup.ipdam.utils

import android.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test


class ColorUtilsTest {

    @Test
    fun test_IntColor_toHexColor() {
        val color = Color.RED
        assertEquals("#FF0000", ColorUtils.getHexColor(color))
    }
}