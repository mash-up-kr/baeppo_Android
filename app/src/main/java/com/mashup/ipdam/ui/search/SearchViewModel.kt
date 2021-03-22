package com.mashup.ipdam.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mashup.base.BaseViewModel
import com.mashup.base.schedulers.SchedulerProvider
import com.mashup.ipdam.ui.search.data.entity.history.History
import com.mashup.ipdam.ui.search.data.entity.kakao.Places
import com.mashup.ipdam.ui.search.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

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
    private val _historyList = MutableLiveData<List<History>>(emptyList())
    val historyList: LiveData<List<History>> = _historyList
    private val _isEditKeyword = MutableLiveData(false)
    val isEditKeyword: LiveData<Boolean> = _isEditKeyword
    private val _isSearchingPlace = MutableLiveData(false)
    val isSearchingPlace = _isSearchingPlace

    fun setKeyword(keyword: String) {
        savedStateHandle["keyword"] = keyword
        this.keyword.value = keyword
    }

    fun getPlaceByEditText() {
        keyword.value?.let {
            if (it.isEmpty()) {
                _isKeywordEmptyOnSearching.value = true
                return
            }
            deleteHistoryBeforeInsert(it)
            getPlaces(it)
        }
    }

    private fun getPlaces(keyword: String) {
        _isKeywordEmptyOnSearching.value = false
        _isSearchingPlace.value = true
        _isEditKeyword.value = false

        searchRepository.getPlaceByKeyword(keyword)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe { placeKeyword ->
                _isSearchingPlace.value = false
                _placeList.value = placeKeyword.places
            }.addToDisposable()
    }

    private fun getHistory() {
        searchRepository.getHistoryAll()
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe{ historyList ->
                _historyList.value = historyList
                if (historyList.size > 10) {
                    deleteHistoryWithPosition(historyList.size - 1)
                }
            }.addToDisposable()
    }

    private fun insertHistory(address: String) {
        val history = History(address = address)

        searchRepository.insertHistory(history)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe()
            .addToDisposable()
    }

    private fun deleteHistoryBeforeInsert(address: String) {
        searchRepository.deleteHistoryWithAddress(address)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe{
                insertHistory(address)
            }.addToDisposable()
    }

    private fun deleteHistory(history: History) {
        searchRepository.deleteHistory(history)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe {
                getHistory()
            }.addToDisposable()
    }

    fun setIsEditKeywordTrue() {
        if (isSearchingPlace.value == true) {
            return
        }
        _isEditKeyword.value = true
        getHistory()
    }

    fun getPlaceByHistoryWithPosition(position: Int) {
        _historyList.value?.get(position)?.let {
            setKeyword(it.address)
            getPlaces(it.address)
        }
    }

    fun deleteHistoryWithPosition(position: Int) {
        _historyList.value?.get(position)?.let {
            deleteHistory(it)
        }
    }

    fun deleteHistoryAll() {
        searchRepository.deleteHistoryAll()
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
            .subscribe {
                _historyList.value = emptyList()
            }.addToDisposable()
    }
}