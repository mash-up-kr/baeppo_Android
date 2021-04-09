package com.mashup.ipdam.utils

object RegexUtil {
    fun getIdRegex() = """^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,12}$""".toRegex()

    fun getPasswordRex() = """^(?=.*[A-Za-z])(?=.*\d)(?=.*[${'$'}@${'$'}!%*#?&])[A-Za-z\d${'$'}@${'$'}!%*#?&]{8,}$""".toRegex()
}