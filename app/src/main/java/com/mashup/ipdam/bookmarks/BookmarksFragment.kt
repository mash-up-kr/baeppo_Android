package com.mashup.ipdam.bookmarks

import com.mashup.base.BaseFragment
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.FragmentBookmarksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : BaseFragment<FragmentBookmarksBinding>(R.layout.fragment_bookmarks) {

    override var logTag: String = "BookmarksFragment"

    companion object {
        fun getInstance() = BookmarksFragment()
    }
}