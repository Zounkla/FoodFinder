package com.example.foodfinder

import androidx.compose.foundation.lazy.LazyColumn
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.foodfinder.ui.component.PermissionDialog
import com.example.foodfinder.ui.component.RestaurantItem
import com.example.foodfinder.ui.component.SearchBar
import com.example.foodfinder.ui.theme.FoodFinderTheme
import com.example.foodfinder.ui.viewmodel.RestaurantViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                viewModel.fetchRestaurants(0.0, 0.0)
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
            Text(text = "Error: ${viewModel.errorMessage.value}",
                color = Color.Red,
                modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewModel.restaurants.value) { restaurant ->
                    RestaurantItem(restaurant)
                }
            }
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


/*private fun checkLocationPermission(
    context: Context,
    isPermissionGranted: Boolean,
    onGranted: () -> Unit,
    onDenied: () -> Unit
) {
    if (isPermissionGranted) {
        onGranted()
        return
    }

    when {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
            onGranted()
        }

        else -> {
            onDenied()
        }
    }
}*/

@Composable
fun LocationPermissionDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        PermissionDialog(
            onDismissRequest = onDismiss,
            onConfirmation = onConfirm,
            dialogTitle = "Permission requise",
            dialogText = "L'application requiert l'accès au GPS pour trouver la marketplace la plus proche",
            dismissButtonText = "J'ai compris",
            confirmButtonText = "Accèder aux réglages",
            icon = Icons.Filled.Place
        )
    }
}