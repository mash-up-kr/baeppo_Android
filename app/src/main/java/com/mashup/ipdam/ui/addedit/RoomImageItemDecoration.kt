package com.mashup.ipdam.ui.addedit

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mashup.ipdam.R

class RoomImageItemDecoration(private val context: Context) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val horizontalSpace = context.resources.getDimension(R.dimen.space_room_image).toInt()
        val gridIndex = (view.layoutParams as? GridLayoutManager.LayoutParams)?.spanIndex ?: 0
        if (gridIndex % 2 == 1) {
            outRect.left = horizontalSpace
        } else {
            outRect.left = 0
        }
    }
}