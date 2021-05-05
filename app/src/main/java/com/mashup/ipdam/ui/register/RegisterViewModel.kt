package com.mashup.ipdam.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mashup.base.BaseViewModel
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.SingleLiveEvent
import com.mashup.ipdam.error.DuplicatedUserException
import com.mashup.ipdam.network.service.UserService
import com.mashup.ipdam.utils.RegexUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userService: UserService
) : BaseViewModel() {
    override var logTag: String = "RegisterViewModel"

    val id = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordCheck = MutableLiveData("")
    private val _isRegisterSuccess = SingleLiveEvent<Unit>()
    val isRegisterSuccess: SingleLiveEvent<Unit> = _isRegisterSuccess
    private val _isRegisterCancel = SingleLiveEvent<Unit>()
    val isRegisterCancel: SingleLiveEvent<Unit> = _isRegisterCancel
    private val _isDuplicatedIdError = SingleLiveEvent<Unit>()
    val isDuplicatedIdError: SingleLiveEvent<Unit> = _isDuplicatedIdError

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

    fun register() {
        if (RegisterInputType.SAFE != idInputType.value ||
            RegisterInputType.SAFE != passwordInputType.value
        ) {
            return
        }
        val id = id.value ?: return
        val password = password.value ?: return

        userService.register(id, password)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe({
                _isRegisterSuccess.value = Unit
            }, { exception ->
                if (exception is DuplicatedUserException) {
                    _isDuplicatedIdError.value = Unit
                }
                Log.e(logTag, exception.stackTraceToString())
            }).addToDisposable()
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