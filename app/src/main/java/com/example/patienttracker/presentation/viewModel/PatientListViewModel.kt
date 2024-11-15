package com.example.patienttracker.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patienttracker.domain.model.Patient
import com.example.patienttracker.domain.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor(
    private val repository: PatientRepository
): ViewModel(){

    private var _patientList = MutableStateFlow<List<Patient>>(emptyList())
    val patientList = _patientList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllPatients().collect{
                _patientList.value = it
            }
        }
    }

    fun deletePatient(patient : Patient){
        viewModelScope.launch {
            repository.delete(patient)
        }
    }
}