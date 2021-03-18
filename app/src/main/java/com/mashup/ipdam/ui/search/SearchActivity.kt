package com.mashup.ipdam.ui.search

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mashup.base.BaseActivity
import com.mashup.base.ext.hideSoftKeyBoard
import com.mashup.base.ext.toast
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivitySearchBinding
import com.mashup.ipdam.ui.search.adapter.PlaceAdapter
import com.mashup.ipdam.ui.search.data.Places
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search),
    PlaceAdapter.PlaceClickListener {
    override var logTag: String = "SearchActivity"

    private val searchViewModel by viewModels<SearchViewModel>()

    private val placeAdapter: PlaceAdapter by lazy { PlaceAdapter(this) }

    override fun initLayout() {
        binding.viewModel = searchViewModel

        binding.searchView.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        searchViewModel.getPlaceByKeyword()
                        true
                    }
                    else -> false
                }
            })
        binding.searchBack.setOnClickListener {
            finishWithNoResult()
        }
        initRecyclerView()
        initKeywordResult()
    }

    private fun initKeywordResult() {
        intent.getStringExtra("keyword")?.let {
            searchViewModel.getPlaceByKeyword()
        }
    }

    private fun finishWithNoResult() {
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun finishWithPlaceResult(place: Places) {
        val intent = intent.apply {
            putExtra("latitude", place.y.toDouble())
            putExtra("longitude", place.x.toDouble())
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initRecyclerView() {
        binding.resultRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = placeAdapter
        }
    }

    override fun observeViewModel() {
        searchViewModel.isSearchingPlace.observe(this) { isSearching ->
            if (isSearching) {
                hideSoftKeyBoard()
            }
        }
        searchViewModel.isSearchKeywordEmpty.observe(this) {
            if (it) {
                toast(getString(R.string.empty_search_address))
            }
        }
    }

    override fun onPlaceClick(position: Int) {
        searchViewModel.placeList.value?.let { placeList ->
            if (placeList.size >= position) {
                finishWithPlaceResult(placeList[position])
            }
        }
    }
}