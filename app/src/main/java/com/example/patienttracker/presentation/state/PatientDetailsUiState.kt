package com.example.patienttracker.presentation.state

data class PatientDetailsUiState (
    val name : String = "",
    val id : Int = 0,
    val age : String = "",
    val prescription : String = "",
    val doctorAssigned : String = "",
    val gender : Int = 0
)