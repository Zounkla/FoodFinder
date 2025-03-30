package com.example.foodfinder.data.model.dto

data class RestaurantListDto(val version: String,
                             val generator: String,
                             val om3s: Any,
                             val elements: List<RestaurantDto>
    ) {

}