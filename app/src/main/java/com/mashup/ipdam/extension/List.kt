package com.mashup.ipdam.extension

import android.net.Uri

fun List<String>.toUriList() = map {
    Uri.parse(it)
}

fun List<Uri>.toStringList() = map {
    it.toString()
}