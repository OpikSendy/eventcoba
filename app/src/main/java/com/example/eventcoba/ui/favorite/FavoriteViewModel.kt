package com.example.eventcoba.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventcoba.data.model.FavoriteEvent
import com.example.eventcoba.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {

    val allFavorites: LiveData<List<FavoriteEvent>> = repository.allFavorites

    fun addFavorite(event: FavoriteEvent) {
        viewModelScope.launch {
            repository.addFavorite(event)
        }
    }

    fun removeFavorite(event: FavoriteEvent) {
        viewModelScope.launch {
            repository.removeFavorite(event)
        }
    }

    fun isFavorite(eventId: Int): LiveData<Boolean> {
        return repository.isFavorite(eventId)
    }
}


