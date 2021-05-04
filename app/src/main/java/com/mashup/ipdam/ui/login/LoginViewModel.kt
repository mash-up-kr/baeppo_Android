package com.mashup.ipdam.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.SingleLiveEvent
import com.mashup.ipdam.data.datastore.AuthorizationDataStore
import com.mashup.ipdam.entity.user.User
import com.mashup.ipdam.network.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userService: UserService,
    private val authorizationDataStore: AuthorizationDataStore
) : BaseViewModel() {
    override var logTag: String = "LoginViewModel"

    private val _showMainViewEvent = SingleLiveEvent<Unit>()
    val showMainViewEvent: SingleLiveEvent<Unit> = _showMainViewEvent
    val inputId = MutableLiveData("")
    val inputPassword = MutableLiveData("")

    fun login() {
        val id = inputId.value ?: return
        val password = inputPassword.value ?: return
        userService.login(id, password)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe(
                { user ->
                    setIdWhenLoginSuccess(user)
                }, { exception ->
                    Log.e(logTag, exception.stackTraceToString())
                }
            ).addToDisposable()
    }

    private fun setIdWhenLoginSuccess(user: User) {
        authorizationDataStore.saveId(user.id)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe(
                {
                    _showMainViewEvent.value = Unit
                }, { exception ->
                    Log.e(logTag, exception.stackTraceToString())
                }
            ).addToDisposable()
    }
}