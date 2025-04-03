package com.example.foodfinder.domain.service

import com.example.foodfinder.data.model.dto.OverpassNodeDto
import com.example.foodfinder.data.model.dto.OverpassResponseDto
import com.example.foodfinder.data.model.dto.DisplayedAdress
import com.example.foodfinder.data.model.dto.DisplayedRestaurant
import com.example.foodfinder.data.source.remote.RestaurantApiService

object RestaurantService {

    suspend fun getRestaurantsByLocation(latitude: Double, longitude: Double) :  List<DisplayedRestaurant>{
        val service = RestaurantApiService()
        val restaurants: OverpassResponseDto = service.getRestaurants(latitude, longitude)
            ?: return emptyList()
        return transformDTOList(restaurants)
    }


    private fun transformDTOList(dto: OverpassResponseDto) : List<DisplayedRestaurant> {
        val result = ArrayList<DisplayedRestaurant>()
        for (restaurant in dto.elements) {
            result.add(transformDTO(restaurant))
        }
        return result.toList()
    }

    private fun transformDTO(dto: OverpassNodeDto) : DisplayedRestaurant {
        return DisplayedRestaurant(dto.id,
                    dto.tags.name ?: "",
                    dto.tags.amenity ?: "",
                            extractAddress(dto))


    }

    private fun extractAddress(dto: OverpassNodeDto) : DisplayedAdress {
        return DisplayedAdress(
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