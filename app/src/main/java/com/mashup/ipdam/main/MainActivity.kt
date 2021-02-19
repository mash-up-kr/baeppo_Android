package com.mashup.ipdam.main

import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.mashup.base.BaseActivity
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivityMainBinding
import com.mashup.ipdam.map.MapFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override var logTag: String = "MainActivity"

    override fun initLayout() {
        supportFragmentManager.commit {
            replace<MapFragment>(R.id.fragment_container_view, "tag")
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

}