package com.mashup.ipdam.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mashup.base.BaseViewModel
import com.mashup.ipdam.SingleLiveEvent

class RegisterViewModel : BaseViewModel() {
    override var logTag: String = "RegisterViewModel"

    val id = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordCheck = MutableLiveData("")
    val searchSchool = MutableLiveData("")
    private val _requestSearchSchool = SingleLiveEvent<Unit>()
    val requestSearchSchool: SingleLiveEvent<Unit> = _requestSearchSchool

    val idCorrect: LiveData<Boolean> = Transformations.map(id) {
        isIdCorrect(it)
    }
    val passwordCheckCorrect: LiveData<Boolean> = Transformations.map(passwordCheck) {
        isPasswordCheckCorrect(it)
    }

    fun showSearchSchoolResult() {
        _requestSearchSchool.value = Unit
    }

    private fun isIdCorrect(id: String): Boolean {
        val idRegex = Regex("[0-9]+[a-zA-z]+")
        val lengthRegex = Regex("[0-9a-zA-Z]{6,12}")
        return idRegex.matches(id) && lengthRegex.matches(id)
    }

    private fun isPasswordCorrect(password: String): Boolean {
        val passwordRegex = Regex("[0-9]+[a-zA-z]+[~!@#$%^&*]+")
        val lengthRegex = Regex("[0-9a-zA-Z~!@#$%^&*]{6,20}")
        return passwordRegex.matches(password) && lengthRegex.matches(password)
    }

    private fun isPasswordCheckCorrect(passwordCheck: String): Boolean {
        val passwordValue = password.value ?: ""
        return if (passwordValue.isEmpty()) false else passwordValue == passwordCheck
    }
}