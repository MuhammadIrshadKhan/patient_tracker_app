package com.example.patienttracker.presentation.navigation

import com.example.patienttracker.utils.Constants

sealed class Screens (val rout : String){
    object PatientListScreen : Screens("patient_list_screen")
    object AddPatientDetailsScreen : Screens("add_patient_details_screen?${Constants.patientId}={${Constants.patientId}}")
    fun passPatientId(patientId: String? = null): String {
        return "add_patient_details_screen?patientId=$patientId"
    }
}