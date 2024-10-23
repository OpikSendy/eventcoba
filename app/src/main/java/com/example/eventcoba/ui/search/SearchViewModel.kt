package com.example.eventcoba.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventcoba.data.model.ListEventsItem
import com.example.eventcoba.data.repository.EventRepository
import com.example.eventcoba.ui.utils.Resource
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: EventRepository) : ViewModel() {

    private val _searchResults = MutableLiveData<Resource<List<ListEventsItem>>>()
    val searchResults: LiveData<Resource<List<ListEventsItem>>> = _searchResults

    fun searchEvents(query: String) {
        viewModelScope.launch {
            _searchResults.postValue(Resource.Loading()) // Indikasi loading
            val response = repository.searchEvents(query)
            _searchResults.postValue(response)
        }
    }
}
