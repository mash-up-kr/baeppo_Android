package com.mashup.ipdam.ui.profile

import android.content.pm.PackageManager
import android.util.Log
import com.mashup.base.BaseFragment
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    override var logTag: String = "ProfileFragment"

    override fun initLayout() {
        initVersionName()
    }

    private fun initVersionName() {
        try {
            val packageInfo = requireContext().packageManager.getPackageInfo(
                requireContext().packageName, 0
            )
            binding.profileVersionValue.text = packageInfo.versionName
        } catch (e : PackageManager.NameNotFoundException) {
            Log.e(logTag, e.message ?: "PackageManagerName Not Found")
        }
    }

    companion object {
        fun getInstance() = ProfileFragment()
    }
}