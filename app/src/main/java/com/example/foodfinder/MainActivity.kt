package com.example.foodfinder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodfinder.ui.component.LocationPermissionDialog
import com.example.foodfinder.ui.component.RestaurantList
import com.example.foodfinder.ui.component.SearchBar
import com.example.foodfinder.ui.theme.FoodFinderTheme
import com.example.foodfinder.ui.viewmodel.LocationViewModel
import com.example.foodfinder.ui.viewmodel.RestaurantViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority


class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationViewModel: LocationViewModel by viewModels()
    private lateinit var locationCallback: LocationCallback


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        enableEdgeToEdge()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val newLocation = locationResult.lastLocation
                if (newLocation != null) {
                    locationViewModel.updateLocation(newLocation)
                }
            }
        }
        setContent {
            FoodFinderTheme {
                when {
                    ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        //continue
                    }

                    else -> {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ),
                            100
                        )

                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    SearchBar(innerPadding)
                }
            }
        }
    }


    @Composable
    fun SearchBar(innerPadding: PaddingValues, viewModel: RestaurantViewModel = viewModel()) {
        var searchText by rememberSaveable { mutableStateOf("") }
        val context = LocalContext.current
        val location = locationViewModel.location.collectAsState().value
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchBar(
                searchText = searchText,
                onSearchTextChanged = { searchText = it },
                onLocationClick = {
                    viewModel.checkLocationPermission(context)
                    if (location != null) {
                        viewModel.fetchRestaurants(location.latitude, location.longitude)
                    } else {
                        viewModel.showLocationErrorDialog.value = true
                    }
                },
                onSearchClick = {
                    println(searchText)
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        println(searchText)
                    }
                )
            )

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

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            forceUpdateLocation()
        }
    }

    private fun forceUpdateLocation() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 1000
        ).build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val updatedLocation = locationResult.lastLocation
                if (updatedLocation != null) {
                    locationViewModel.updateLocation(updatedLocation)
                    println("Localisation mise à jour : $updatedLocation")
                }
            }
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, mainLooper)
        }
    }
}