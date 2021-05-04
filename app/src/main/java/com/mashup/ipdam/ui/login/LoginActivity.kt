package com.mashup.ipdam.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.mashup.base.BaseActivity
import com.mashup.base.ext.toast
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivityLoginBinding
import com.mashup.ipdam.ui.main.MainActivity
import com.mashup.ipdam.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override var logTag: String = "LoginActivity"
    private val loginViewModel: LoginViewModel by viewModels()

    override fun initLayout() {
        binding.loginRegisterAccount.setOnClickListener {
            showRegisterView()
        }
        binding.loginFindAccount.setOnClickListener {
            toast(R.string.login_find_not_working)
        }
    }

    override fun observeViewModel() {
        loginViewModel.apply {
            showMainViewEvent.observe(this@LoginActivity) {
                showMainView()
            }
        }
    }

    private fun showMainView() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showRegisterView() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }
}