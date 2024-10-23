package com.example.eventcoba.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventcoba.data.model.ListEventsItem
import com.example.eventcoba.data.repository.EventRepository
import com.example.eventcoba.ui.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: EventRepository) : ViewModel() {

    private val _activeEvents = MutableLiveData<Resource<List<ListEventsItem>>>()
    val activeEvents: LiveData<Resource<List<ListEventsItem>>> = _activeEvents

    private val _pastEvents = MutableLiveData<Resource<List<ListEventsItem>>>()
    val pastEvents: LiveData<Resource<List<ListEventsItem>>> = _pastEvents

    fun fetchActiveEvents() {
        viewModelScope.launch {
            _activeEvents.value = Resource.Loading()
            val result = repository.getActiveEvents()

            if (result is Resource.Success) {
                val limitedEvents = result.data ?: emptyList()
                Log.d("ActiveEvents", "Total events after limit: ${limitedEvents.size}")
                _activeEvents.value = Resource.Success(limitedEvents.take(5))
            } else if (result is Resource.Error) {
                _activeEvents.value = result
            }
        }
    }


    fun fetchPastEvents() {
        viewModelScope.launch {
            _pastEvents.value = Resource.Loading()
            try {
                when (val result = repository.getPastEvents()) {
                    is Resource.Success -> {
                        val limitedEvents = result.data?.take(5) ?: emptyList()
                        _pastEvents.value = Resource.Success(limitedEvents)
                    }
                    is Resource.Error -> {
                        _pastEvents.value = result
                    }
                    is Resource.Loading -> {
                        // This case is unlikely if repository returns final result
                        _pastEvents.value = Resource.Loading()
                    }
                }
            } catch (e: Exception) {
                _pastEvents.value = Resource.Error("An error occurred: ${e.message}")
            }
        }
    }
}
