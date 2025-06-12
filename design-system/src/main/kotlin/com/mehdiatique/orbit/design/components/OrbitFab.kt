package com.mehdiatique.orbit.design.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable

@Composable
fun OrbitFab(
    contentDescription: String,
    onClick: () -> Unit
) {
    LargeFloatingActionButton(onClick = onClick) {
        Icon(Icons.Default.Add, contentDescription = contentDescription)
    }
}
