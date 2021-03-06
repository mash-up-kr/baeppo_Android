package com.mashup.ipdam.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mashup.base.BaseViewModel
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.ui.search.data.Documents
import com.mashup.ipdam.ui.search.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel() {
    override var logTag: String = "SearchViewModel"
    val keyword = MutableLiveData("")

    private val _placeList = MutableLiveData<List<Documents>>(emptyList())
    val placeList: LiveData<List<Documents>> = _placeList
    private val _isSearchingPlace = MutableLiveData(false)
    val isSearchingPlace = _isSearchingPlace

    fun getPlaceByKeyword() {
        _isSearchingPlace.value = true
        keyword.value?.let {
            searchRepository.getPlaceByKeyword(it)
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .subscribe{ placeKeyword ->
                    _isSearchingPlace.value = false
                    _placeList.value = placeKeyword.documents
                }
        }
    }
}