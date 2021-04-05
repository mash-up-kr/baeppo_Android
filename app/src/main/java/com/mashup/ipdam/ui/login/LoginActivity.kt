package com.mashup.ipdam.ui.login

import android.content.Intent
import com.mashup.base.BaseActivity
import com.mashup.base.ext.setDecorFitStatusBar
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivityLoginBinding
import com.mashup.ipdam.ui.main.MainActivity
import com.mashup.ipdam.ui.register.RegisterActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override var logTag: String = "LoginActivity"

    override fun initLayout() {
        setDecorFitStatusBar()
        binding.loginButton.setOnClickListener {
            //TODO: 로그인 로직
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.loginRegisterAccount.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.loginFindAccount.setOnClickListener {
            //TODO: 아이디 및 비밀번호 찾끼 로직
        }
    }
}