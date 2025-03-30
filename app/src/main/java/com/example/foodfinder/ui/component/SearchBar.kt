package com.example.foodfinder.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onLocationClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        modifier = modifier
            .fillMaxWidth(),
        leadingIcon = {
            IconButton(onClick = onLocationClick) {
                Icon(
                    imageVector = Icons.Outlined.Place,
                    contentDescription = "Location Icon"
                )
            }
        },
        trailingIcon =
        {
            IconButton(onClick =
            {
                focusManager.clearFocus()
                onSearchClick()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search Icon"
                )
            }
        },
        placeholder = { Text(text = "Rechercher par nom de ville...") },
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = keyboardActions,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
        ),
    )
}