package com.mashup.ipdam.ui.home

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mashup.base.ext.setHtmlText
import com.mashup.ipdam.R
import com.mashup.ipdam.utils.ColorUtils

@BindingAdapter(value = ["ipdamAddress", "ipdamCount"], requireAll = true)
fun showIpdamHeader(view: TextView, address: String, count: Int) {
    val mainColor = ResourcesCompat.getColor(view.resources, R.color.main_color, null)
    val mainHexColor = ColorUtils.getHexColor(mainColor)
    view.setHtmlText(
        view.context.getString(
            R.string.ipdam_header_text,
            address,
            mainHexColor,
            count
        )
    )
}

@BindingAdapter("bottomSheetVisible")
fun setVisibleBottomSheet(bottomSheetRootView: ConstraintLayout, visible: Boolean) {
    BottomSheetBehavior.from(bottomSheetRootView).run {
        state = if (visible) BottomSheetBehavior.STATE_COLLAPSED else BottomSheetBehavior.STATE_HIDDEN
    }
}