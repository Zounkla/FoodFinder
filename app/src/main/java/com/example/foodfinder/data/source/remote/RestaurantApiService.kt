package com.example.foodfinder.data.source.remote

import com.example.foodfinder.data.model.dto.OverpassResponseDto
import com.example.foodfinder.domain.service.OverpassApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantApiService {

    suspend fun getRestaurants(latitude: Double, longitude: Double): OverpassResponseDto? {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://overpass-api.de/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(OverpassApiService::class.java)
        val query = """
            [out:json];
            node["amenity"="restaurant"](around:500,$latitude,$longitude);
            out body;
            >;
            out skel qt;
        """.trimIndent()
        println(query)
        var result: OverpassResponseDto? = null
        try {
            val response = withContext(Dispatchers.IO) {
                service.getMapData(query)
            }
            result = response
        } catch (e: Exception) {
            println("Error fetching data: ${e.message}")
        }
        return result
    }
}