package com.mashup.ipdam.ui.register

import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.mashup.base.BaseActivity
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    override var logTag: String = "RegisterActivity"
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun initLayout() {
        binding.apply {
            viewModel = registerViewModel
            registerBackButton.setOnClickListener {
                finish()
            }
            registerButton.setOnClickListener {
                //TODO: 회원가입 로직 작성
            }
        }
        initSearchEditText()
    }

    private fun initSearchEditText() {
        binding.registerSearchButton.setOnClickListener {
            showSearchSchoolView()
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
        registerViewModel.idCorrect.observe(this) { isCorrect ->
            if (isCorrect) {
                showIdCheckSuccessLayout()
            } else {
                showIdCheckFailedLayout()
            }
        }
        registerViewModel.passwordCheckCorrect.observe(this) { isCorrect ->
            if (isCorrect) {
                showPasswordCheckSuccessLayout()
            } else {
                showPasswordCheckFailedLayout()
            }
        }
        registerViewModel.requestSearchSchool.observe(this) {
            showSearchSchoolView()
        }
    }

    private fun showSearchSchoolView() {
        //TODO: 학교 검색 화면
    }

    private fun showIdCheckSuccessLayout() {
        binding.registerIdResult.run {
            visibility = View.VISIBLE
            text = getString(R.string.register_id_result_success)
            setTextColor(ContextCompat.getColor(context, R.color.primary_color))
        }
    }

    private fun showIdCheckFailedLayout() {
        binding.registerIdResult.run {
            visibility = View.VISIBLE
            text = getString(R.string.register_id_result_failed)
            setTextColor(ContextCompat.getColor(context, R.color.red_color))
        }
    }

    private fun showPasswordCheckSuccessLayout() {
        binding.registerPasswordResult.run {
            visibility = View.VISIBLE
            text = getString(R.string.register_password_result_success)
            setTextColor(ContextCompat.getColor(context, R.color.primary_color))
        }
    }

    private fun showPasswordCheckFailedLayout() {
        binding.registerPasswordResult.run {
            visibility = View.VISIBLE
            text = getString(R.string.register_password_result_failed)
            setTextColor(ContextCompat.getColor(context, R.color.red_color))
        }
    }
}