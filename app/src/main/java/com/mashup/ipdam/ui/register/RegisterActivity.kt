package com.mashup.ipdam.ui.register

import android.view.View
import androidx.core.content.ContextCompat
import com.mashup.base.BaseActivity
import com.mashup.base.ext.setDecorFitStatusBar
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    override var logTag: String = "RegisterActivity"

    override fun initLayout() {
        setDecorFitStatusBar()
        binding.registerBackButton.setOnClickListener {
            finish()
        }

        binding.registerButton.setOnClickListener {
            //TODO: 회원가입 로직 작성
        }
    }

    fun showIdCheckSuccessLayout() {
        binding.registerIdResult.run {
            visibility = View.VISIBLE
            text = getString(R.string.register_id_result_success)
            setTextColor(ContextCompat.getColor(context, R.color.primary_color))
        }
    }

    fun showIdCheckFailedLayout() {
        binding.registerIdResult.run {
            visibility = View.VISIBLE
            text = getString(R.string.register_id_result_failed)
            setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }

    fun showPasswordCheckSuccessLayout() {
        binding.registerPasswordResult.run {
            visibility = View.VISIBLE
            text = getString(R.string.register_password_result_success)
            setTextColor(ContextCompat.getColor(context, R.color.primary_color))
        }
    }

    fun showPasswordCheckFailedLayout() {
        binding.registerPasswordResult.run {
            visibility = View.VISIBLE
            text = getString(R.string.register_password_result_failed)
            setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }
}