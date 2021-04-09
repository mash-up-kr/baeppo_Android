package com.mashup.ipdam.utils

import org.junit.Test
import org.junit.Assert.*

class RegexUtilsTest {

    @Test
    fun test_idRegex() {
        val idRegex = RegexUtil.getIdRegex()
        var id = "abdcd"
        assertEquals(idRegex.matches(id), false)
        id = "121minuk"
        assertEquals(idRegex.matches(id), true)
        id = "minjung12"
        assertEquals(idRegex.matches(id), true)
    }

    @Test
    fun test_passwordRegex() {
        val passwordRex = RegexUtil.getPasswordRex()
        var password = "password12"
        assertEquals(passwordRex.matches(password), false)
        password = "password12&&"
        assertEquals(passwordRex.matches(password), true)
    }
}