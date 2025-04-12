package com.example.foodfinder.domain.service

import android.location.Location
import com.example.foodfinder.data.model.dto.OverpassNodeDto
import com.example.foodfinder.data.model.dto.OverpassResponseDto
import com.example.foodfinder.data.model.dto.DisplayedAdress
import com.example.foodfinder.data.model.dto.DisplayedRestaurant
import com.example.foodfinder.data.source.remote.RestaurantApiService
import java.util.Locale
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.atan2
import kotlin.math.cos

object RestaurantService {

    suspend fun getRestaurantsByLocation(location: Location) :  List<DisplayedRestaurant>{
        val service = RestaurantApiService()
        val restaurants: OverpassResponseDto = service.getRestaurants(location)
            ?: return emptyList()
        return transformDTOList(restaurants, location)
    }


    private fun transformDTOList(dto: OverpassResponseDto,location: Location) : List<DisplayedRestaurant> {
        val result = ArrayList<DisplayedRestaurant>()
        for (restaurant in dto.elements) {
            result.add(transformDTO(restaurant, location))
        }
        return result.sortedBy { it.distance }
    }

    private fun transformDTO(dto: OverpassNodeDto, location: Location) : DisplayedRestaurant {
        val displayedAdress = extractAddress(dto)
        val distance = String.format(
            Locale.US,
            "%.2f",
            computeDistance(displayedAdress, location)
        ).toDouble()
        return DisplayedRestaurant(dto.id,
                    dto.tags.name ?: "",
                    dto.tags.amenity ?: "",
                            displayedAdress,
                            distance
            )


    }

    private fun extractAddress(dto: OverpassNodeDto) : DisplayedAdress {
        return DisplayedAdress(
            dto.tags.addr_city ?: "",
            dto.tags.addr_country ?: "",
            dto.tags.addr_housenumber ?: "",
            dto.tags.addr_postcode ?: "",
            dto.tags.addr_street ?: "",
            dto.lon.toString(),
            dto.lat.toString()
        )
    }

    private fun computeDistance(displayedAdress: DisplayedAdress,
                                location: Location) : Double {
        val lat1 = displayedAdress.lat.toDouble()
        val lon1 = displayedAdress.lon.toDouble()
        val lat2 = location.latitude
        val lon2 = location.longitude
        val r = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }
}