package com.mashup.ipdam.ui.addedit.dialog

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

class AmenitiesDialog private constructor(
    private val addListener: ((String) -> Unit)?,
    private val cancelListener: (() -> Unit)?
): DialogFragment() {

    private lateinit var binding: DialogAmenitiesBinding

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
                cancelListener?.invoke()
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

    class Builder {
        private var addListener: ((String) -> Unit)? = null
        private var cancelListener: (() -> Unit)? = null

        fun setAddListener(listener: (String) -> Unit): Builder {
            addListener = listener
            return this
        }

        fun setCancelListener(listener: () -> Unit): Builder {
            cancelListener = listener
            return this
        }

        fun build() : AmenitiesDialog {
            return AmenitiesDialog(addListener, cancelListener)
        }
    }
}