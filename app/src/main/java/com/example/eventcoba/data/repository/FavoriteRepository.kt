package com.example.eventcoba.data.repository

import androidx.lifecycle.LiveData
import com.example.eventcoba.data.local.FavoriteEventDao
import com.example.eventcoba.data.model.FavoriteEvent
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteEventDao: FavoriteEventDao
) {
    val allFavorites: LiveData<List<FavoriteEvent>> = favoriteEventDao.getAllFavorites()

    suspend fun addFavorite(event: FavoriteEvent) {
        favoriteEventDao.insertFavorite(event)
    }

    suspend fun removeFavorite(event: FavoriteEvent) {
        favoriteEventDao.deleteFavorite(event)
    }

    fun isFavorite(eventId: Int): LiveData<Boolean> {
        return favoriteEventDao.isFavorite(eventId)
    }
}