package com.mashup.ipdam.error

import androidx.annotation.StringRes
import com.mashup.ipdam.R

enum class Error(@StringRes val imageErrorMessage: Int) {
    DUPLICATED_IMAGE(R.string.error_duplicated_room_image)
}