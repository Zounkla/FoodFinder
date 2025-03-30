package com.example.foodfinder.data.source.remote

import com.example.foodfinder.data.model.dto.RestaurantListDto
import com.example.foodfinder.domain.service.OverpassApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantApiService {

    suspend fun getRestaurants(): RestaurantListDto? {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://overpass-api.de/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(OverpassApiService::class.java)
        val query = """
            [out:json];
            node["amenity"="fast_food"](49.4,1.0,49.5,1.1);
            out;
        """.trimIndent()
        var result: RestaurantListDto? = null
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