package com.example.foodfinder.ui.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodfinder.config.FoodFinderDatabase
import com.example.foodfinder.data.model.dto.DisplayedRestaurant
import com.example.foodfinder.data.model.entity.VisitedRestaurant
import com.example.foodfinder.data.repository.VisitedRestaurantRepository
import com.example.foodfinder.domain.service.RestaurantService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RestaurantViewModel(application: Application) : AndroidViewModel(application) {

    var restaurants = mutableStateOf<List<DisplayedRestaurant>>(emptyList())
    private var isPermissionGranted = mutableStateOf(false)
    var showLocationErrorDialog = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)
    private val db = FoodFinderDatabase.getDatabase(application)
    private val repository = VisitedRestaurantRepository(db.visitedRestaurantDao())
    private var _visitedRestaurants = MutableStateFlow<List<VisitedRestaurant>>(emptyList())
    var visitedRestaurants: StateFlow<List<VisitedRestaurant>> = _visitedRestaurants

    init {
        loadVisitedRestaurants()
    }

    private fun loadVisitedRestaurants() {
        viewModelScope.launch(Dispatchers.IO) {
            _visitedRestaurants.value = repository.getAll()
        }
    }
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

    fun click(restaurant: DisplayedRestaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isRestaurantVisited(restaurant)) {
                val visitedRestaurant = VisitedRestaurant(restaurant.id)
                repository.insert(visitedRestaurant)
            } else {
                val visitedRestaurant = repository.getRestaurantById(restaurant.id)
                repository.delete(visitedRestaurant!!)
            }
            updateVisitedRestaurants()
        }
    }

    suspend fun isRestaurantVisited(restaurant: DisplayedRestaurant): Boolean {
        return repository.getRestaurantById(restaurant.id) != null
    }

    private fun updateVisitedRestaurants() {
        viewModelScope.launch(Dispatchers.IO) {
            val visited = repository.getAll()
            _visitedRestaurants.value = visited
        }
    }
}