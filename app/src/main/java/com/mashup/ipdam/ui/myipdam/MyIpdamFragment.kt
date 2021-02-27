package com.mashup.ipdam.ui.myipdam

import com.mashup.base.BaseFragment
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.FragmentMyipdamBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyIpdamFragment : BaseFragment<FragmentMyipdamBinding>(R.layout.fragment_myipdam) {

    override var logTag: String = "MyIpdamFragment"

    companion object {
        fun getInstance() = MyIpdamFragment()
    }
}