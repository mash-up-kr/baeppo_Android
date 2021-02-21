package com.mashup.ipdam.home

import com.mashup.base.BaseFragment
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override var logTag: String = "MapFragment"

    companion object {
        fun getInstance() = HomeFragment()
    }
}