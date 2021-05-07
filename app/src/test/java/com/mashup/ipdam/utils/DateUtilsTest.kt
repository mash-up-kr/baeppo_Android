package com.mashup.ipdam.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateUtilsTest {

    @Test
    fun test_DateFormatter_isSuccess() {
        val dataFormat = DateUtil.getDateFormatter()
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2020)
            set(Calendar.MONTH, 0)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        assertEquals("2020.01.01", dataFormat.format(Date(calendar.timeInMillis)))
    }
}