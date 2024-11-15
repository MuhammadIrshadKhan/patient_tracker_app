package com.example.patienttracker.presentation.sereens

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun DeleteDialogue(
    title: String,
    message: String,
    onDialogDismiss: () -> Unit,
    onDialogConfirm: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.body1
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onDialogConfirm() // Correct invocation
            }) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDialogDismiss() // Correct invocation
            }) {
                Text(text = "Cancel")
            }
        },
        onDismissRequest = {
            onDialogDismiss() // Correct invocation
        }
    )
}
