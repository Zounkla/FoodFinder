package com.example.foodfinder.ui.component

import android.location.Location
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.foodfinder.data.model.entity.Restaurant

@Composable
fun RestaurantItem(restaurant: Restaurant, location: Location?) {
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 700),
        label = "FadeIn"
    )

    val cardElevation = animateFloatAsState(
        targetValue = 10f,
        animationSpec = tween(durationMillis = 500),
        label = "Elevation"
    )

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(cardElevation.value.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp)
                .graphicsLayer {
                    this.alpha = alpha
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icône de localisation
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Store Icon",
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                // Nom du restaurant
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (location == null) {
                    Text(
                        text = "Aucune localisation disponible",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
