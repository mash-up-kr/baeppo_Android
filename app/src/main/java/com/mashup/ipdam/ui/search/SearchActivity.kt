package com.mashup.ipdam.ui.search

import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.mashup.base.BaseActivity
import com.mashup.base.ext.hideSoftKeyBoard
import com.mashup.base.ext.toast
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivitySearchBinding
import com.mashup.ipdam.entity.kakao.keyword.Place
import com.mashup.ipdam.ui.search.adapter.history.HistoryAdapter
import com.mashup.ipdam.ui.search.adapter.kakao.PlaceAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search),
    PlaceAdapter.PlaceClickListener {
    override var logTag: String = "SearchActivity"

    private val placeAdapter: PlaceAdapter by lazy { PlaceAdapter(this) }
    private val historyAdapter: HistoryAdapter by lazy { HistoryAdapter(searchViewModel) }
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun initLayout() {
        binding.viewModel = searchViewModel

        initRecyclerView()
        initKeywordResult()
        initButton()
        initEditText()
    }

    private fun initEditText() {
        binding.searchView.apply {
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        searchViewModel.getPlaceByEditText()
                        true
                    }
                    else -> false
                }
            }

            doOnTextChanged { _, _, _, _ ->
                searchViewModel.setIsEditKeywordTrue()
            }
        }
    }

    private fun initKeywordResult() {
        intent.getStringExtra("keyword")?.let {
            searchViewModel.getPlaceByEditText()
        }
    }

    private fun initButton() {
        binding.searchBack.setOnClickListener {
            finishWithNoResult()
        }
        binding.historyClearButton.setOnClickListener {
            searchViewModel.deleteHistoryAll()
        }
    }

    private fun finishWithNoResult() {
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun finishWithPlaceResult(place: Place) {
        val intent = intent.apply {
            putExtra("latitude", place.y.toDouble())
            putExtra("longitude", place.x.toDouble())
            putExtra("address", place.addressName)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initRecyclerView() {
        binding.resultRecyclerView.apply {
            adapter = placeAdapter
        }
        binding.historyRecyclerView.apply {
            adapter = historyAdapter
        }
    }


    override fun observeViewModel() {
        searchViewModel.isSearchingPlace.observe(this) { isSearching ->
            if (isSearching) {
                hideSoftKeyBoard()
            }
        }
        searchViewModel.isKeywordEmptyOnSearching.observe(this) {
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