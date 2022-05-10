package com.demo.kaamelott.presentation.components

import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DividerComponent(
    modifier: Modifier,
    alpha: Float = 0.08f
) {
    Divider(
        modifier = modifier,
        color = MaterialTheme.colors.onSurface.copy(alpha = alpha)
    )
}