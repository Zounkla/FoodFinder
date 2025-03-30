package com.example.foodfinder.ui.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodfinder.data.model.entity.Restaurant
import com.example.foodfinder.domain.service.RestaurantService
import kotlinx.coroutines.launch

class RestaurantViewModel : ViewModel() {

    var restaurants = mutableStateOf<List<Restaurant>>(emptyList())
    var isPermissionGranted = mutableStateOf(false)
    var showLocationErrorDialog = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)

    fun checkLocationPermission(context: Context) {
        isPermissionGranted.value = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun fetchRestaurants(latitude: Double, longitude: Double) {
        if (!isPermissionGranted.value) {
            showLocationErrorDialog.value = true
            return
        }

        isLoading.value = true
        viewModelScope.launch {
            try {
                restaurants.value = RestaurantService.getRestaurantsByLocation(latitude, longitude)
            } catch (e: Exception) {
                errorMessage.value = "Failed to fetch data: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}