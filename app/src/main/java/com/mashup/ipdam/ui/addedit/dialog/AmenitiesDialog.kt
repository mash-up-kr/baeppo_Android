package com.mashup.ipdam.ui.addedit.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.DialogAmenitiesBinding


class AmenitiesDialog private constructor(): DialogFragment() {

    private lateinit var binding: DialogAmenitiesBinding
    private var addListener: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAmenitiesBinding.inflate(inflater, container, false).apply {
            dialogAmenitiesButton.setOnClickListener {
                addListener?.invoke(dialogAmenitiesEdit.text.toString())
                dismiss()
            }
            dialogCancelButton.setOnClickListener {
                dismiss()
            }
        }

        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.apply {
            setLayout(width, height)
        }
    }

    private fun setAddListener(listener: (String) -> Unit) {
        addListener = listener
    }

    class Builder(val context: Context) {
        private val dialog = AmenitiesDialog()

        fun setAddListener(listener: (String) -> Unit): Builder {
            dialog.setAddListener(listener)
            return this
        }

        fun build() : AmenitiesDialog {
            return dialog
        }
    }
}