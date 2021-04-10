package com.mashup.ipdam.ui.myipdam

import androidx.fragment.app.activityViewModels
import com.mashup.base.BaseFragment
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.FragmentBookmarksBinding
import com.mashup.ipdam.databinding.FragmentMyipdamBinding
import com.mashup.ipdam.ui.bookmark.BookmarkViewModel
import com.mashup.ipdam.ui.bookmark.adapter.BookmarkReviewAdapter
import com.mashup.ipdam.ui.myipdam.adapter.MyIpdamReviewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyIpdamFragment : BaseFragment<FragmentMyipdamBinding>(R.layout.fragment_myipdam) {

    override var logTag: String = "MyIpdamsFragment"
    private val myIpdamReviewAdapter by lazy { MyIpdamReviewAdapter(myIpdamViewModel) }
    private val myIpdamViewModel by activityViewModels<MyIpdamViewModel>()

    companion object {
        fun getInstance() = MyIpdamFragment()
    }

    override fun initLayout() {
        super.initLayout()
        binding.viewModel = myIpdamViewModel
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.rvMyIpdamReview.adapter = myIpdamReviewAdapter
        myIpdamViewModel.getMyIpdamReviews()
    }
}