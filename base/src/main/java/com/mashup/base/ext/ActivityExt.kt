package com.mashup.base.ext

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowInsetsController


fun Activity.setDecorFitStatusBar() {
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.run {
                setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            }
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