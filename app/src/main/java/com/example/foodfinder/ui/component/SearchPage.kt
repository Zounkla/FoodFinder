package com.example.foodfinder.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodfinder.ui.viewmodel.LocationViewModel
import com.example.foodfinder.ui.viewmodel.RestaurantViewModel

@Composable
fun SearchPage(innerPadding: PaddingValues, locationViewModel: LocationViewModel) {
    Box(modifier = Modifier.padding(innerPadding)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            SearchBar(locationViewModel)
        }
    }
}

@Composable
fun SearchBar(locationViewModel: LocationViewModel,
              viewModel: RestaurantViewModel = viewModel()) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val location = locationViewModel.location.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar(
            searchText = searchText,
            onSearchTextChanged = { searchText = it },
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
    }
}