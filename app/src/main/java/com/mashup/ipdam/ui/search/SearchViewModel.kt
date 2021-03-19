package com.mashup.ipdam.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mashup.base.BaseViewModel
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.ui.search.data.entity.kakao.Places
import com.mashup.ipdam.ui.search.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    override var logTag: String = "SearchViewModel"
    val keyword = savedStateHandle.getLiveData<String>("keyword")

    private val _placeList = MutableLiveData<List<Places>>(emptyList())
    val placeList: LiveData<List<Places>> = _placeList
    private val _isKeywordEmptyOnSearching = MutableLiveData(false)
    val isKeywordEmptyOnSearching = _isKeywordEmptyOnSearching
    private val _isSearchingPlace = MutableLiveData(false)
    val isSearchingPlace = _isSearchingPlace

    fun setKeyword(keyword: String) {
        savedStateHandle["keyword"] = keyword
    }

    fun getPlaceByKeyword() {
        keyword.value?.let {
            if (it.isEmpty()) {
                _isKeywordEmptyOnSearching.value = true
                return
            }
            _isKeywordEmptyOnSearching.value = false
            _isSearchingPlace.value = true

            searchRepository.getPlaceByKeyword(it)
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .subscribe{ placeKeyword ->
                    _isSearchingPlace.value = false
                    _placeList.value = placeKeyword.places
                }
        }
    }
}