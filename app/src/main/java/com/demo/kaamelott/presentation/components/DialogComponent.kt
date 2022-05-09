package com.demo.kaamelott.presentation.components

import androidx.annotation.StringRes
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.demo.kaamelott.R

@Composable
fun NotAvailableFeaturePopup(
    @StringRes message: Int,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = stringResource(id = message),
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.close))
            }
        }
    )
}