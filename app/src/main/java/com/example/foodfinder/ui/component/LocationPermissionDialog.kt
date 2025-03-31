package com.example.foodfinder.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable

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
            dialogText = "L'application a besoin d'accéder au GPS pour trouver les restaurants autour de votre position.",
            dismissButtonText = "J'ai compris",
            confirmButtonText = "Accéder aux réglages",
            icon = Icons.Filled.Place
        )
    }
}

