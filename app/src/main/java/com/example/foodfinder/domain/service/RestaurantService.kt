package com.example.foodfinder.domain.service

import com.example.foodfinder.data.model.dto.RestaurantDto
import com.example.foodfinder.data.model.dto.RestaurantListDto
import com.example.foodfinder.data.model.entity.Adress
import com.example.foodfinder.data.model.entity.Restaurant
import com.example.foodfinder.data.source.remote.RestaurantApiService

object RestaurantService {

    suspend fun getRestaurantsByLocation(latitude: Double, longitude: Double) :  List<Restaurant>{
        val service = RestaurantApiService()
        val restaurants: RestaurantListDto = service.getRestaurants(latitude, longitude)
            ?: return emptyList()
        return transformDTOList(restaurants)
    }


    private fun transformDTOList(dto: RestaurantListDto) : List<Restaurant> {
        val result = ArrayList<Restaurant>()
        for (restaurant in dto.elements) {
            result.add(transformDTO(restaurant))
        }
        return result.toList()
    }

    private fun transformDTO(dto: RestaurantDto) : Restaurant {
        return Restaurant(dto.id,
                    dto.tags.name ?: "",
                    dto.tags.amenity ?: "",
                            extractAddress(dto))


    }

    private fun extractAddress(dto: RestaurantDto) : Adress {
        return Adress(
            dto.tags.addr_city ?: "",
            dto.tags.addr_country ?: "",
            dto.tags.addr_housenumber ?: "",
            dto.tags.addr_postcode ?: "",
            dto.tags.addr_street ?: "",
            dto.long.toString(),
            dto.lat.toString()
        )
    }
}