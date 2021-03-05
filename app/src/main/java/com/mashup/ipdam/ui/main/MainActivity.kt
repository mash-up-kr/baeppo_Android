package com.mashup.ipdam.ui.main

import android.graphics.Color
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.commit
import com.mashup.base.BaseActivity
import com.mashup.base.ext.initStatusBarTransparent
import com.mashup.ipdam.R
import com.mashup.ipdam.ui.bookmark.BookmarkFragment
import com.mashup.ipdam.databinding.ActivityMainBinding
import com.mashup.ipdam.ui.home.HomeFragment
import com.mashup.ipdam.ui.myipdam.MyIpdamFragment
import com.mashup.ipdam.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override var logTag: String = "MainActivity"

    private val homeFragment by lazy { HomeFragment.getInstance() }
    private val bookmarksFragment by lazy { BookmarkFragment.getInstance() }
    private val myIpdamFragment by lazy { MyIpdamFragment.getInstance() }
    private val profileFragment by lazy { ProfileFragment.getInstance() }

    override fun initLayout() {
        initStatusBarTransparent()
        navigateFragment(MainType.HOME)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationListener)
    }

    private val bottomNavigationListener: (menu: MenuItem) -> Boolean = { menu ->
        binding.bottomNavigationIndicator
            .selectedItemId = menu.itemId

        when (menu.itemId) {
            R.id.action_home -> {
                navigateFragment(MainType.HOME)
                setStatusBarTransparent()
                true
            }
            R.id.action_bookmarks -> {
                navigateFragment(MainType.BOOKMARKS)
                setStatusBarColor()
                true
            }
            R.id.action_myipdam -> {
                navigateFragment(MainType.MYIPDAM)
                setStatusBarColor()
                true
            }
            R.id.action_profile -> {
                navigateFragment(MainType.PROFILE)
                setStatusBarColor()
                true
            }
            else -> false
        }
    }

    private fun setStatusBarTransparent() {
        binding.statusBar.apply {
            setBackgroundColor(ResourcesCompat.getColor(resources, R.color.status_color, null))
            elevation = 1f
        }
        binding.statusBar.elevation = 0f
    }

    private fun setStatusBarColor() {
        binding.statusBar.apply {
            setBackgroundColor(statusBarColor)
            elevation = 0f
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

    companion object {
        const val statusBarColor = Color.WHITE
    }
}