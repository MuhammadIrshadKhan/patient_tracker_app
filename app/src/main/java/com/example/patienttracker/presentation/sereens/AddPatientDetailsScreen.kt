package com.example.patienttracker.presentation.sereens


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.patienttracker.presentation.events.PatientTrackerEvents
import com.example.patienttracker.presentation.viewModel.PatientDetailsViewModel
import com.example.patienttracker.presentation.viewModel.UiEvent
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddPatientDetailsScreen(
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    patientId: Int? = null,
    viewModel: PatientDetailsViewModel = hiltViewModel() // Ensure Hilt is injecting the ViewModel
) {
    val patientDetailsState = viewModel.state
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(patientId) {
        if (patientId != null && patientId != -1) {
            viewModel.fetchPatientDetails(patientId)
        }
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collectLatest {event ->
            when(event){
                UiEvent.SaveNote -> {
                    onSaveClicked()
                    Toast.makeText(context,"Note Saved Successfully",Toast.LENGTH_SHORT).show()
                }
                is UiEvent.showToast -> {
                    Toast.makeText(context,event.message,Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    Scaffold(
        topBar = { MyAppBar(onBackClicked = onBackClicked) },
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
                .imePadding()
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = patientDetailsState.name, // Use the state to get the current value
                onValueChange = { newValue ->
                    viewModel.onEvent(PatientTrackerEvents.EnterPatientName(newValue))
                },
                label = { Text(text = "Name") },
                textStyle = MaterialTheme.typography.body1.copy(color = Color.Black),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = patientDetailsState.age, // Use the state to get the current value
                    onValueChange = { newValue ->
                        viewModel.onEvent(PatientTrackerEvents.EnterPatientAge(newValue))
                    },
                    label = { Text(text = "Age") },
                    textStyle = MaterialTheme.typography.body1.copy(color = Color.Black),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
                )

                Spacer(modifier = Modifier.width(10.dp))

                RadioButtonGroup(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Male",
                    selected = patientDetailsState.gender == 1,
                    onClick = {
                        viewModel.onEvent(PatientTrackerEvents.selectMale)
                    }
                )

                RadioButtonGroup(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Female",
                    selected = patientDetailsState.gender == 2,
                    onClick = {
                        viewModel.onEvent(PatientTrackerEvents.selectFeMale)
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = patientDetailsState.doctorAssigned, // Use the state to get the current value
                onValueChange = { newValue ->
                    viewModel.onEvent(PatientTrackerEvents.EnterPatientDoctorAssigned(newValue))
                },
                label = { Text(text = "Doctor Assigned") },
                textStyle = MaterialTheme.typography.body1.copy(color = Color.Black),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = patientDetailsState.prescription, // Use the state to get the current value
                onValueChange = { newValue ->
                    viewModel.onEvent(PatientTrackerEvents.EnterPatientPrescription(newValue))
                },
                label = { Text(text = "Doctor's Prescription") },
                textStyle = MaterialTheme.typography.body1.copy(color = Color.Black),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onEvent(PatientTrackerEvents.selectSaveButton)
                }
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun RadioButtonGroup(
    modifier: Modifier,
    text : String,
    selected : Boolean,
    onClick: () -> Unit,

) {
    Row (modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically){
        RadioButton(selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary
            )
            )
        Text(text = text,
            style = MaterialTheme.typography.body1)
    }
}

@Composable
fun MyAppBar(onBackClicked : () -> Unit){
    TopAppBar(
        title = {
            Text(
                text = "Enter Patient Details",
                style = MaterialTheme.typography.h6
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) { // Corrected to invoke the callback
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun showDetails(){
//    AddPatientDetailsScreen()
//}