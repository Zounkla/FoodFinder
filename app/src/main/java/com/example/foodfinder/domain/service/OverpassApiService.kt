package com.example.foodfinder.domain.service

import com.example.foodfinder.data.model.dto.OverpassResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OverpassApiService {
    @GET("api/interpreter")
    suspend fun getMapData(
        @Query("data") query: String
    ): OverpassResponseDto
}
