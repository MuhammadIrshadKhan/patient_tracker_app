package com.example.patienttracker.presentation.events

sealed class PatientTrackerEvents {

    data class EnterPatientName(val name : String) : PatientTrackerEvents()
    data class EnterPatientAge(val age : String) : PatientTrackerEvents()
    data class EnterPatientPrescription(val prescription : String) : PatientTrackerEvents()
    data class EnterPatientDoctorAssigned(val doctorAssigned : String) : PatientTrackerEvents()

    object selectMale : PatientTrackerEvents()
    object selectFeMale : PatientTrackerEvents()
    object selectSaveButton : PatientTrackerEvents()

}