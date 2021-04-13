package com.mashup.ipdam.ui.creator

import com.mashup.base.BaseActivity
import com.mashup.base.ext.setDecorFitStatusBar
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivityCreatorBinding

class CreatorActivity : BaseActivity<ActivityCreatorBinding>(R.layout.activity_creator) {
    override var logTag: String = "CreatorActivity"

    override fun initLayout() {
        binding.creatorBackButton.setOnClickListener {
            finish()
        }
    }
}