package com.mashup.ipdam.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.SingleLiveEvent
import com.mashup.ipdam.utils.RegexUtil

class RegisterViewModel : BaseViewModel() {
    override var logTag: String = "RegisterViewModel"

    val id = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordCheck = MutableLiveData("")
    val searchSchool = MutableLiveData("")
    private val _requestSearchSchool = SingleLiveEvent<Unit>()
    val requestSearchSchool: SingleLiveEvent<Unit> = _requestSearchSchool
    val idInputType: LiveData<RegisterInputType> =
        Transformations.map(id) { id ->
            isIdCorrect(id)
        }
    val passwordInputType: LiveData<RegisterInputType> =
        Transformations.map(password) { password ->
            isPasswordCorrect(password)
        }
    val passwordCheckInputType: LiveData<RegisterInputType> =
        Transformations.map(passwordCheck) { passwordCheck ->
            isPasswordCheckCorrect(passwordCheck)
        }

    fun showSearchSchoolResult() {
        _requestSearchSchool.value = Unit
    }

    private fun isIdCorrect(id: String): RegisterInputType {
        if (id.isEmpty()) return RegisterInputType.NONE

        val idRegex = RegexUtil.getIdRegex()
        return if (idRegex.matches(id)) RegisterInputType.SAFE else RegisterInputType.WRONG
    }

    private fun isPasswordCorrect(password: String): RegisterInputType {
        if (password.isEmpty()) return RegisterInputType.NONE

        val passwordRegex = RegexUtil.getPasswordRex()
        return if (passwordRegex.matches(password)) RegisterInputType.SAFE else RegisterInputType.WRONG
    }

    private fun isPasswordCheckCorrect(passwordCheck: String): RegisterInputType {
        if (passwordCheck.isEmpty()) return RegisterInputType.NONE

        val passwordValue = password.value ?: ""
        return if (passwordValue == passwordCheck) RegisterInputType.SAFE
        else RegisterInputType.WRONG
    }
}