package com.example.patienttracker.presentation.sereens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.patienttracker.domain.model.Patient
import com.example.patienttracker.presentation.viewModel.PatientListViewModel


@Composable
fun PatientListScreen(
    onFabClicked: () -> Unit,
    onItemClicked : (Int?) ->Unit,
    viewMode : PatientListViewModel = hiltViewModel(),
) {
    val patientList by viewMode.patientList.collectAsState()
    Scaffold(
        topBar = { appBar() },
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top)),

                floatingActionButton = { flButton(onFabClicked = onFabClicked)
                               } // Add your floating action button here
    ) { paddingValues ->
        LazyColumn (modifier =
        Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .imePadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(patientList) { patient ->
                PatientItemScreen(patient = patient,
                    onItemClicked = {onItemClicked(patient.id)},
                    onDeleteConfirm = {viewMode.deletePatient(patient)}

                )
            }

        }
        if(patientList.isEmpty()) {

            // Use paddingValues to avoid content being under the AppBar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues), // Apply padding values to the content
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Enter patients details by\npressing the add button",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun showScreen() {
//    PatientListScreen()
//
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appBar(){
    androidx.compose.material.TopAppBar(title = {
        androidx.compose.material.Text(
            text = "Enter Patient Details",
            style = androidx.compose.material.MaterialTheme.typography.h6
        )
    })

}

@Composable
fun flButton(
    onFabClicked : () -> Unit
){
    FloatingActionButton(onClick = onFabClicked) {
        Icon(imageVector = Icons.Filled.Add,
            contentDescription = "add patients")

    }
}


