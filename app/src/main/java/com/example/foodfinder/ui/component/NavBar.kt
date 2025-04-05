package com.example.foodfinder.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.foodfinder.ui.theme.LightGray
import com.example.foodfinder.ui.theme.MidnightBlue
import com.example.foodfinder.ui.theme.SoftGold

@Composable
fun NavBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = MidnightBlue,
        contentColor = SoftGold,
        modifier = Modifier.height(80.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(imageVector = Icons.Filled.Search,
                    contentDescription = "Recherche",
                    modifier = Modifier.size(32.dp)
                        .align(Alignment.CenterVertically)
                )
            },
            selected = selectedItem == 0,
            onClick = { onItemSelected(0) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SoftGold,
                unselectedIconColor = LightGray
            )
        )
        NavigationBarItem(
            icon = {
                Icon(imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Géolocalisation",
                    modifier = Modifier.size(32.dp)
                        .align(Alignment.CenterVertically)
                )
            },
            selected = selectedItem == 1,
            onClick = { onItemSelected(1) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SoftGold,
                unselectedIconColor = LightGray
            )
        )
        NavigationBarItem(
            icon = {
                Icon(imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Restaurants visités",
                    modifier = Modifier.size(32.dp)
                        .align(Alignment.CenterVertically)
                )
            },
            selected = selectedItem == 2,
            onClick = { onItemSelected(2) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SoftGold,
                unselectedIconColor = LightGray
            )
        )
        NavigationBarItem(
            icon = {
                Image(
                    painter = rememberAsyncImagePainter("android.resource://com.example.foodfinder/drawable/equalizer"),
                    contentDescription = "Statistiques",
                    modifier = Modifier.size(32.dp)
                        .align(Alignment.CenterVertically)
                )
            },
            selected = selectedItem == 3,
            onClick = { onItemSelected(3) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SoftGold,
                unselectedIconColor = LightGray
            )
        )
    }
}
