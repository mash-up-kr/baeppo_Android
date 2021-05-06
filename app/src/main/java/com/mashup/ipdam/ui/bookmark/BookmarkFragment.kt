package com.mashup.ipdam.ui.bookmark

import androidx.fragment.app.activityViewModels
import com.mashup.base.BaseFragment
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.FragmentBookmarksBinding
import com.mashup.ipdam.ui.bookmark.adapter.BookmarkReviewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarksBinding>(R.layout.fragment_bookmarks) {

    override var logTag: String = "BookmarksFragment"
    private val bookmarkReviewAdapter by lazy { BookmarkReviewAdapter(bookmarkViewModel) }
    private val bookmarkViewModel by activityViewModels<BookmarkViewModel>()

    companion object {
        fun getInstance() = BookmarkFragment()
    }

    override fun initLayout() {
        super.initLayout()
        initRecyclerView()
        binding.viewModel = bookmarkViewModel
    }

    private fun initRecyclerView(){
        binding.rvBookmarkReview.adapter = bookmarkReviewAdapter
    }

    override fun onResume() {
        super.onResume()
        bookmarkViewModel.getBookmarkReviews()
    }
}