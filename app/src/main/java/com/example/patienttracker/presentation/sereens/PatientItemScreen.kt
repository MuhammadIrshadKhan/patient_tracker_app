package com.example.patienttracker.presentation.sereens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.patienttracker.domain.model.Patient

@Composable
fun PatientItemScreen(
    patient: Patient,
    onItemClicked: () -> Unit,
    onDeleteConfirm: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteDialogue(
            title = "Delete",
            message = "Do you really want to delete patient ${patient.name} from the list?",
            onDialogDismiss = { showDialog = false },
            onDialogConfirm = {
                onDeleteConfirm() // Delete the patient
                showDialog = false // Dismiss the dialog after confirming
            }
        )
    }

    Card(
        modifier = Modifier
            .clickable { onItemClicked() }
            .fillMaxSize()
            .padding(10.dp),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(9f)) {
                Text(
                    text = patient.name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Assigned to Dr: ${patient.doctorAssign}",
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(modifier = Modifier.weight(1f), onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
            }
        }
    }
}
