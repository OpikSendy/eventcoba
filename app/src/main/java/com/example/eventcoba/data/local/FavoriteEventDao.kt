package com.example.eventcoba.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eventcoba.data.model.FavoriteEvent

@Dao
interface FavoriteEventDao {
    @Query("SELECT * FROM favorite_events")
    fun getAllFavorites(): LiveData<List<FavoriteEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(event: FavoriteEvent)

    @Delete
    suspend fun deleteFavorite(event: FavoriteEvent)

    @Query("DELETE FROM favorite_events WHERE id = :eventId")
    suspend fun deleteFavoriteById(eventId: Int)

    @Query("SELECT COUNT(*) FROM favorite_events WHERE id = :eventId")
    suspend fun getFavoriteCount(eventId: Int): Int

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_events WHERE id = :eventId)")
    fun isFavorite(eventId: Int): LiveData<Boolean>
}