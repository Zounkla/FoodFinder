package com.example.foodfinder.ui.component

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.foodfinder.data.model.dto.DisplayedRestaurant
import com.example.foodfinder.ui.viewmodel.RestaurantViewModel

@Composable
fun RestaurantList(displayedRestaurants: List<DisplayedRestaurant>,
                   location: Location?,
                   viewModel: RestaurantViewModel) {
    val visitedRestaurants by viewModel.visitedRestaurants.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(displayedRestaurants) { restaurant ->
            val isVisited = visitedRestaurants.any { it.id == restaurant.id }
            RestaurantItem(restaurant, location,
                onClick = { viewModel.click(restaurant)},
                isVisited)
        }
    }
}
