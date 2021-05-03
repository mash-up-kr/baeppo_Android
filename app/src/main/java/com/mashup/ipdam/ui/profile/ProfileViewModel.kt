package com.mashup.ipdam.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor() : BaseViewModel() {
    override var logTag: String = "ProfileViewModel"

    //TODO: 사용자 정보 불러오기 ex) 이름

    private val _profileImageUrl = MutableLiveData<String>()
    val profileImageUrl: MutableLiveData<String> = _profileImageUrl

    fun setProfileImage(uri: Uri) {
        _profileImageUrl.value = uri.toString()
    }
}