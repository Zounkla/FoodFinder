package com.example.foodfinder.data.model.dto

data class DisplayedRestaurant(
    val id: Long,
    val name: String,
    val category: String,
    val address: DisplayedAdress

) {
}