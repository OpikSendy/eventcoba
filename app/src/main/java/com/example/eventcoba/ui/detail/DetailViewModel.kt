package com.example.eventcoba.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventcoba.data.model.ListEventsItem
import com.example.eventcoba.data.repository.EventRepository
import com.example.eventcoba.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _eventDetails = MutableLiveData<Resource<ListEventsItem>>()
    val eventDetails: LiveData<Resource<ListEventsItem>> = _eventDetails

    fun setEventDetails(event: ListEventsItem) {
        _eventDetails.value = Resource.Success(event)
    }

//    fun refreshEventDetails(id: String) {
//        viewModelScope.launch {
//            _eventDetails.value = Resource.Loading()
//            _eventDetails.value = repository.getEventDetails(id)
//        }
//    }
}
