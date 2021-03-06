package com.mashup.base.ext

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS


fun Activity.setStatusBarTransparent() {
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.show(WindowInsets.Type.navigationBars())
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        else -> {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }
    window.statusBarColor = Color.TRANSPARENT
}