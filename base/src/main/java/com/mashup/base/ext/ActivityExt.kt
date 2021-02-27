package com.mashup.base.ext

import android.app.Activity
import android.graphics.Color
import android.view.View


fun Activity.setStatusBarTransparent() {
    //TODO: status bar 확장하는 코드 Deprecated 되어서 변경해야함!
    window?.decorView?.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    window.statusBarColor = Color.TRANSPARENT
}