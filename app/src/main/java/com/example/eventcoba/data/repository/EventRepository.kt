package com.example.eventcoba.data.repository

import android.util.Log
import com.example.eventcoba.data.model.ListEventsItem
import com.example.eventcoba.data.service.Interface
import com.example.eventcoba.ui.utils.Resource
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val apiService: Interface
) {

    suspend fun getActiveEvents(): Resource<List<ListEventsItem>> {
        return try {
            val response = apiService.getActiveEvents()
            Log.d("EventRepository", "Response: $response") // Log the response
            if (response.isSuccessful) {
                Resource.Success(response.body()?.listEvents ?: emptyList())
            } else {
                Resource.Error("An error occurred")
            }
        } catch (e: Exception) {
            Resource.Error("Failed to load data")
        }
    }


    suspend fun getPastEvents(): Resource<List<ListEventsItem>> {
        return try {
            val response = apiService.getPastEvents()
            Log.d("EventRepository", "Response: $response") // Log the response
            if (response.isSuccessful) {
                Resource.Success(response.body()?.listEvents ?: emptyList())
            } else {
                Resource.Error("An error occurred")
            }
        } catch (e: Exception) {
            Resource.Error("Failed to load data")
        }
    }


    suspend fun searchEvents(query: String): Resource<List<ListEventsItem>> {
        return try {
            val response = apiService.getSearchEvents(query = query)
            if (response.isSuccessful) {
                Resource.Success(response.body()?.listEvents ?: emptyList())
            } else {
                Resource.Error("An error occurred")
            }
        } catch (e: Exception) {
            Resource.Error("Failed to load data")
        }
    }


    suspend fun getEventDetails(id: String): Resource<ListEventsItem> {
        return try {
            val response = apiService.getEventDetails(id)
            if (response.isSuccessful) {
                Resource.Success(response.body() ?: throw Exception("No data found"))
            } else {
                Resource.Error("An error occurred")
            }
        } catch (e: Exception) {
            Resource.Error("Failed to load data")
        }
    }
}