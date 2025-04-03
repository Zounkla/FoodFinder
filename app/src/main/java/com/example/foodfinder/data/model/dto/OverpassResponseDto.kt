package com.example.foodfinder.data.model.dto

data class OverpassResponseDto(val version: String,
                               val generator: String,
                               val om3s: Any,
                               val elements: List<OverpassNodeDto>
    ) {

}