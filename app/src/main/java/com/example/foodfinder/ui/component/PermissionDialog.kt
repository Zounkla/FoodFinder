package com.example.foodfinder.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun PermissionDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    dismissButtonText: String,
    confirmButtonText: String,
    icon: ImageVector,
) {

    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            icon = {
                Icon(icon, contentDescription = "Icon")
            },
            title = {
                Text(text = dialogTitle)
            },
            text = {
                Text(text = dialogText)
            },
            onDismissRequest = {
                openDialog.value = false
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onConfirmation()
                    }
                ) {
                    Text(confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onDismissRequest()
                    }
                ) {
                    Text(dismissButtonText)
                }
            }
        )
    }

}