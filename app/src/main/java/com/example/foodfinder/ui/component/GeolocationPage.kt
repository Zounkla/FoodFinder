package com.example.foodfinder.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodfinder.ui.viewmodel.LocationViewModel
import com.example.foodfinder.ui.viewmodel.RestaurantViewModel


@Composable
fun GeolocationPage(innerPadding: PaddingValues, locationViewModel: LocationViewModel,
                    viewModel: RestaurantViewModel = viewModel()
) {
    val context = LocalContext.current
    val location = locationViewModel.location.collectAsState().value
    val hasFetchedRestaurants = remember { mutableStateOf(false) }
    viewModel.checkLocationPermission(context)
    LaunchedEffect(Unit) {
        if (location != null && !hasFetchedRestaurants.value) {
            viewModel.fetchRestaurants(location)
            hasFetchedRestaurants.value = true
        } else {
            viewModel.showLocationErrorDialog.value = true
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        if (viewModel.isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else if (viewModel.errorMessage.value != null) {
            Text(
                text = "Error: ${viewModel.errorMessage.value}",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            RestaurantList(viewModel.restaurants.value, location, viewModel)
        }


        if (viewModel.showLocationErrorDialog.value) {
            LocationPermissionDialog(
                showDialog = viewModel.showLocationErrorDialog.value,
                onDismiss = { viewModel.showLocationErrorDialog.value = false },
                onConfirm = {
                }
            )
        }
    }
}