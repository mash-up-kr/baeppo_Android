package com.mashup.ipdam.main

import android.view.MenuItem
import androidx.fragment.app.commit
import com.mashup.base.BaseActivity
import com.mashup.ipdam.R
import com.mashup.ipdam.bookmarks.BookmarksFragment
import com.mashup.ipdam.databinding.ActivityMainBinding
import com.mashup.ipdam.home.HomeFragment
import com.mashup.ipdam.myipdam.MyIpdamFragment
import com.mashup.ipdam.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override var logTag: String = "MainActivity"

    private val homeFragment by lazy { HomeFragment.getInstance() }
    private val bookmarksFragment by lazy { BookmarksFragment.getInstance() }
    private val myIpdamFragment by lazy { MyIpdamFragment.getInstance() }
    private val profileFragment by lazy { ProfileFragment.getInstance() }

    override fun initLayout() {
        navigateFragment(MainType.HOME)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationListener)
    }

    private val bottomNavigationListener: (menu: MenuItem) -> Boolean = { menu ->
        when (menu.itemId) {
            R.id.action_home -> {
                navigateFragment(MainType.HOME)
                true
            }
            R.id.action_bookmarks -> {
                navigateFragment(MainType.BOOKMARKS)
                true
            }
            R.id.action_myipdam -> {
                navigateFragment(MainType.MYIPDAM)
                true
            }
            R.id.action_profile -> {
                navigateFragment(MainType.PROFILE)
                true
            }
            else -> false
        }
    }

    private fun navigateFragment(mainType: MainType) {
        val fragment = getFragment(mainType)
        val primaryFragment = supportFragmentManager.primaryNavigationFragment

        supportFragmentManager.commit {
            show(fragment)
            primaryFragment?.let { supportFragmentManager.commit { hide(it) } }
            setPrimaryNavigationFragment(fragment)
        }
    }

    private fun getFragment(mainType: MainType) = when (mainType) {
        MainType.HOME -> homeFragment
        MainType.BOOKMARKS -> bookmarksFragment
        MainType.MYIPDAM -> myIpdamFragment
        MainType.PROFILE -> profileFragment
    }.apply {
        if (!isAdded) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frameLayout, this).commit()
        }
    }

}