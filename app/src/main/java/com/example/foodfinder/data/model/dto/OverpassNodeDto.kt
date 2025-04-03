package com.example.foodfinder.data.model.dto

data class OverpassNodeDto(val type: String,
                           val id: Long,
                           val lat: Double,
                           val long: Double,
                           val tags: OverpassTagsDto)    {

}