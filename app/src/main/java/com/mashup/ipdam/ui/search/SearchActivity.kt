package com.mashup.ipdam.ui.search

import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.databinding.library.baseAdapters.BR
import com.mashup.base.BaseActivity
import com.mashup.base.BaseRecyclerView
import com.mashup.base.ext.hideSoftKeyBoard
import com.mashup.base.ext.toast
import com.mashup.ipdam.R
import com.mashup.ipdam.databinding.ActivitySearchBinding
import com.mashup.ipdam.databinding.ItemHistoryBinding
import com.mashup.ipdam.databinding.ItemPlaceBinding
import com.mashup.ipdam.ui.search.data.entity.history.History
import com.mashup.ipdam.ui.search.data.entity.kakao.Places
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {
    override var logTag: String = "SearchActivity"

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

    private fun finishWithPlaceResult(place: Places) {
        val intent = intent.apply {
            putExtra("latitude", place.y.toDouble())
            putExtra("longitude", place.x.toDouble())
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initRecyclerView() {
        binding.resultRecyclerView.adapter =
            object : BaseRecyclerView.Adapter<Places, ItemPlaceBinding>(R.layout.item_place,
                BR.document,
                object : BaseRecyclerView.ItemCallback<Places>() {
                    override fun areItemsTheSame(oldItem: Places, newItem: Places): Boolean {
                        return oldItem.id == newItem.id
                    }
                }) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): BaseRecyclerView.ViewHolder<ItemPlaceBinding> {
                    val viewHolder = super.onCreateViewHolder(parent, viewType)
                    viewHolder.binding.root.setOnClickListener {
                        searchViewModel.placeList.value?.let { placeList ->
                            if (placeList.size >= viewHolder.adapterPosition) {
                                finishWithPlaceResult(placeList[viewHolder.adapterPosition])
                            }
                        }
                    }
                    return viewHolder
                }
            }
        binding.historyRecyclerView.adapter =
            object : BaseRecyclerView.Adapter<History, ItemHistoryBinding>(R.layout.item_history,
                BR.history, object : BaseRecyclerView.ItemCallback<History>() {
                    override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                        return oldItem.id == newItem.id
                    }
                }) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): BaseRecyclerView.ViewHolder<ItemHistoryBinding> {
                    val viewHolder = super.onCreateViewHolder(parent, viewType)
                    viewHolder.binding.apply {
                        root.setOnClickListener {
                            searchViewModel.getPlaceByHistoryWithPosition(viewHolder.adapterPosition)
                            historyDelete.setOnClickListener {
                                searchViewModel.deleteHistoryWithPosition(viewHolder.adapterPosition)
                            }
                        }
                    }
                    return viewHolder
                }
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
}