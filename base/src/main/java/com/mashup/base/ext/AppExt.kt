package com.mashup.base.ext

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Context.toast(message: CharSequence?) {
    if (message.isNullOrEmpty()) return
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes messageId: Int) {
    Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show()
}

fun Context.getStatusBarHeight() : Int {
    val resourceId = resources.getIdentifier("status_bar_height",
        "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
}

fun Context.getNavigationBarHeight() : Int {
    val resourceId = resources.getIdentifier("navigation_bar_height",
        "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
}

fun Fragment.toast(message: CharSequence?) {
    if (message.isNullOrEmpty()) return
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(@StringRes messageId: Int) {
    Toast.makeText(requireContext(), messageId, Toast.LENGTH_SHORT).show()
}

fun Activity.showSoftKeyBoard() {
    val view = currentFocus
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

fun FragmentActivity.showSoftKeyBoard() {
    val view = currentFocus
    val imm =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

fun Activity.hideSoftKeyBoard() {
    val view = currentFocus
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
    view?.clearFocus()
}

fun FragmentActivity.hideSoftKeyBoard() {
    val view = currentFocus
    val imm =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    view?.clearFocus()
}