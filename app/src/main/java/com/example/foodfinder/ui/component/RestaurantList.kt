package com.example.foodfinder.ui.component

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.foodfinder.data.model.entity.Restaurant

@Composable
fun RestaurantList(restaurants: List<Restaurant>, location: Location?) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(restaurants) { restaurant ->
            RestaurantItem(restaurant, location)
        }
    }
}
