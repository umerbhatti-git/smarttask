package com.example.smarttask.interfaces

import com.example.smarttask.models.MainModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getData(): MainModel
}
