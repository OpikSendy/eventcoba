package com.example.eventcoba.data.service

import com.example.eventcoba.data.model.EventResponse
import com.example.eventcoba.data.model.ListEventsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Interface {
    @GET("events")
    suspend fun getActiveEvents(@Query("active") active: Int = 1): Response<EventResponse>

    @GET("events")
    suspend fun getPastEvents(@Query("active") active: Int = 0): Response<EventResponse>

    @GET("events")
    suspend fun getSearchEvents(@Query("q") query: String, @Query("active") active: Int = -1): Response<EventResponse>

    @GET("events/{id}")
    suspend fun getEventDetails(@Path("id") id: String): Response<ListEventsItem>

    @GET("events")
    suspend fun getNearestActiveEvent(
        @Query("active") active: Int = -1, // -1 artinya semua event aktif
        @Query("limit") limit: Int = 1 // Hanya ambil 1 event terdekat
    ): Response<EventResponse>
}