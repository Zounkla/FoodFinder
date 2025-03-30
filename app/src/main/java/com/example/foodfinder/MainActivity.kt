package com.example.foodfinder

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.foodfinder.ui.component.PermissionDialog
import com.example.foodfinder.ui.component.SearchBar
import com.example.foodfinder.ui.theme.FoodFinderTheme


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
fun SearchBar(innerPadding: PaddingValues) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var isPermissionGranted by rememberSaveable { mutableStateOf(false) }
    var showLocationErrorDialog by rememberSaveable { mutableStateOf(false) }
    var userTriggeredSearch by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        SearchBar(
            searchText = searchText,
            onSearchTextChanged = { searchText = it },
            onLocationClick = {
                searchText = ""
                checkLocationPermission(
                    context = context,
                    isPermissionGranted = isPermissionGranted,
                    onGranted = {
                        isPermissionGranted = true
                        println("GEO")
                    },
                    onDenied = {
                        showLocationErrorDialog = true
                    }
                )
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
    }

    LocationPermissionDialog(
        showDialog = showLocationErrorDialog,
        onDismiss = { showLocationErrorDialog = false },
        onConfirm = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.fromParts("package", context.packageName, null)
            context.startActivity(intent)
            showLocationErrorDialog = false
        }
    )
}

private fun checkLocationPermission(
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
}

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