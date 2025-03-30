package com.example.foodfinder.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodfinder.data.model.entity.Restaurant

@Composable
fun RestaurantItem(restaurant: Restaurant) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = restaurant.name)
    }
}