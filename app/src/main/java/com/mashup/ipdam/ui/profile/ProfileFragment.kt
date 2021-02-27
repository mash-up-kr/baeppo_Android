package com.mashup.ipdam.ui.profile

import com.mashup.base.BaseFragment
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    override var logTag: String = "ProfileFragment"

    companion object {
        fun getInstance() = ProfileFragment()
    }
}