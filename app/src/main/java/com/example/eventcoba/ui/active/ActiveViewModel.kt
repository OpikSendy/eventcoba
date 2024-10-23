package com.example.eventcoba.ui.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventcoba.data.model.ListEventsItem
import com.example.eventcoba.data.repository.EventRepository
import com.example.eventcoba.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _events = MutableLiveData<Resource<List<ListEventsItem>>>()
    val events: LiveData<Resource<List<ListEventsItem>>> = _events

    fun fetchEvents() {
        viewModelScope.launch {
            _events.value = Resource.Loading()
            val result = repository.getActiveEvents()
            _events.value = result
        }
    }
}