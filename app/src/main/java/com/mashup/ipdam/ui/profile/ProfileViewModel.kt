package com.mashup.ipdam.ui.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.SingleLiveEvent
import com.mashup.ipdam.data.datastore.UserDataStore
import com.mashup.ipdam.entity.user.User
import com.mashup.ipdam.error.NotFoundUserException
import com.mashup.ipdam.network.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userService: UserService,
    private val userDataStore: UserDataStore
) : BaseViewModel() {
    override var logTag: String = "ProfileViewModel"

    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User> = _userInfo
    private val _isLogoutSuccess = SingleLiveEvent<Unit>()
    val isLogoutSuccess: SingleLiveEvent<Unit> = _isLogoutSuccess
    private val _isNotFoundUser = SingleLiveEvent<Unit>()
    val isNotFoundUser: SingleLiveEvent<Unit> = _isNotFoundUser

    fun initUser() {
        getPrimaryIdWithAction { primaryId ->
            loadUser(primaryId)
        }
    }

    fun logOut() {
        userDataStore.saveId("none")
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe({
                _isLogoutSuccess.value = Unit
            }, { exception ->
                Log.e(logTag, exception.stackTraceToString())
            }).addToDisposable()
    }

    fun setProfileImage(imageUri: Uri) {
      getPrimaryIdWithAction { primaryId ->
          setImageUrl(primaryId, imageUri)
      }
    }

    private fun getPrimaryIdWithAction(action: (String) -> Unit) {
        userDataStore.getId()
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe({
                action(it)
            }, { exception ->
                Log.e(logTag, exception.stackTraceToString())
            }).addToDisposable()
    }

    private fun loadUser(primaryId: String) {
        userService.getUser(primaryId)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe({
                _userInfo.value = it
            }, { exception ->
                if (exception is NotFoundUserException) {
                    _isNotFoundUser.value = Unit
                }
                Log.e(logTag, exception.stackTraceToString())
            }).addToDisposable()
    }

    private fun setImageUrl(primaryId: String, imageUri: Uri) {
        userService.setImageUrlWithUri(primaryId, imageUri)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe({
               loadUser(primaryId)
            }, { exception ->
                Log.e(logTag, exception.stackTraceToString())
            }).addToDisposable()
    }
}