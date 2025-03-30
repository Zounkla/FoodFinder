package com.example.foodfinder.data.model.dto

data class RestaurantDto(val type: String,
                    val id: Number,
                    val lat: Double,
                    val long: Double,
                    val tags: TagDto)    {

}