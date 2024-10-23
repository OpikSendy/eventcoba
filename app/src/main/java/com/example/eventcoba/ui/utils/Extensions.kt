package com.example.eventcoba.ui.utils

import com.example.eventcoba.data.model.FavoriteEvent
import com.example.eventcoba.data.model.ListEventsItem

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