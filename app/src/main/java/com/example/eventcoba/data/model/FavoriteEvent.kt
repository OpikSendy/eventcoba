package com.example.eventcoba.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_events")
class FavoriteEvent(
    @PrimaryKey val id: Int,
    val name: String,
    val ownerName: String,
    val beginTime: String,
    val description: String,
    val imageLogo: String
)

fun FavoriteEvent.toListEventsItem(): ListEventsItem {
    return ListEventsItem(
        summary = this.name,
        mediaCover = this.imageLogo,
        registrants = 0,
        imageLogo = this.imageLogo,
        link = "", // Set sesuai kebutuhan
        description = this.description,
        ownerName = this.ownerName,
        cityName = "", // Set sesuai kebutuhan
        quota = 0, // Set sesuai kebutuhan
        name = this.name,
        id = this.id,
        beginTime = this.beginTime,
        endTime = "", // Set sesuai kebutuhan
        category = "" // Set sesuai kebutuhan
    )
}