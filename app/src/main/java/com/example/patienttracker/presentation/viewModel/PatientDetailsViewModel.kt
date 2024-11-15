package com.example.patienttracker.presentation.viewModel

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patienttracker.domain.model.Patient
import com.example.patienttracker.domain.repository.PatientRepository
import com.example.patienttracker.presentation.events.PatientTrackerEvents
import com.example.patienttracker.presentation.state.PatientDetailsUiState
import com.example.patienttracker.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class PatientDetailsViewModel @Inject constructor(
    private val repository: PatientRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(PatientDetailsUiState())
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private var currentPatientId: Int? = null

    init {
        val id = savedStateHandle.get<Int>(Constants.patientId)
        if (id != null) {
            fetchPatientDetails(id)
        } else {
            // Emit a UiEvent to show a toast message when the patientId is null or invalid
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.showToast("Invalid or missing patient ID"))
            }
        }
    }

    fun onEvent(event: PatientTrackerEvents) {
        when (event) {
            is PatientTrackerEvents.EnterPatientAge -> {
                state = state.copy(age = event.age) // Update with the new age
            }

            is PatientTrackerEvents.EnterPatientDoctorAssigned -> {
                state = state.copy(doctorAssigned = event.doctorAssigned) // Update with the new doctorAssigned
            }

            is PatientTrackerEvents.EnterPatientName -> {
                state = state.copy(name = event.name) // Update with the new name
            }

            is PatientTrackerEvents.EnterPatientPrescription -> {
                state = state.copy(prescription = event.prescription) // Update with the new prescription
            }

            PatientTrackerEvents.selectFeMale -> {
                state = state.copy(gender = 2) // Correct gender value for female
            }

            PatientTrackerEvents.selectMale -> {
                state = state.copy(gender = 1) // Correct gender value for male
            }

            PatientTrackerEvents.selectSaveButton -> {
                viewModelScope.launch {
                    try {
                        SaveData()
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.showToast(
                                message = e.message ?: "Couldn't save data"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun SaveData() {
        val age = state.age.toIntOrNull()
        when {
            state.name.isEmpty() -> throw MyException("Please enter name")
            state.age.isEmpty() -> throw MyException("Please enter age")
            state.gender == 0 -> throw MyException("Please select gender")
            state.doctorAssigned.isEmpty() -> throw MyException("Please enter doctor name")
            state.prescription.isEmpty() -> throw MyException("Please enter prescription")
            age == null || age < 0 || age > 100 -> throw MyException("Please enter correct age")
        }
        val trimmedName = state.name.trim()
        val trimmedDoctorAssigned = state.name.trim()

        viewModelScope.launch {
            repository.addOrUpdate(
                patient = Patient(
                    name = trimmedName,
                    age = trimmedDoctorAssigned,
                    gender = state.gender,
                    doctorAssign = state.doctorAssigned,
                    prescription = state.prescription,
                    id = currentPatientId
                )
            )
        }
    }

    fun fetchPatientDetails(patientId: Int) {
        viewModelScope.launch {
            repository.getPatientById(patientId)?.let { patient ->
                state = state.copy(
                    name = patient.name,
                    age = patient.age,
                    gender = patient.gender,
                    doctorAssigned = patient.doctorAssign,
                    prescription = patient.prescription
                )
                currentPatientId = patientId
            }
        }
    }
}

class MyException(message: String?) : Exception(message)

sealed class UiEvent {
    data class showToast(val message: String?) : UiEvent()
    object SaveNote : UiEvent()
}
